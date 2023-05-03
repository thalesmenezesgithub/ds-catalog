package com.sistema.dscatalog.services;

import com.sistema.dscatalog.dto.CategoryDTO;
import com.sistema.dscatalog.dto.ProductDTO;
import com.sistema.dscatalog.entities.Category;
import com.sistema.dscatalog.entities.Product;
import com.sistema.dscatalog.repositories.CategoryRepository;
import com.sistema.dscatalog.repositories.ProductRepository;
import com.sistema.dscatalog.services.exceptions.DataBaseException;
import com.sistema.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService
{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public ProductDTO save(ProductDTO productDTO)
    {
        Product product = new Product();
        copyDtoToEntity(productDTO, product);

        if(product.getCategories().size() == 0)
        {
            Category category = categoryRepository.getReferenceById(1L);
            product.getCategories().add(category);
        }

        product = productRepository.save(product);

        return  new ProductDTO(product);
    }

    @Transactional
    public List<ProductDTO> findAll()
    {
        List<Product> listProduct = productRepository.findAll();

        List<ProductDTO> listDTO = new ArrayList<>();
        for(Product product : listProduct)
        {
            listDTO.add(new ProductDTO(product));
        }

        return listDTO;
    }

    /*
     * Busca Paginada
     */
    @Transactional
    public Page<Product> findAllPaged(Pageable pageable)
    {
        return productRepository.findAll(pageable);
    }


    /*
     * Busca por id
     */
    @Transactional
    public ProductDTO findById(Long id)
    {
        Optional<Product> optional = productRepository.findById(id);

        //Se não achar o id da categoria lança uma exceção
        Product product = optional.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada!"));

        //Retorna com informações do produto e a categoria que o produto pertence
        return new ProductDTO(product, product.getCategories());
    }

    /*
     * Atualiza
     */
    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO)
    {
       try
       {
           Product product = productRepository.getOne(id);
           copyDtoToEntity(productDTO, product);

           if (product.getCategories().size() == 0)
           {
               Category cat = categoryRepository.getOne(1L);
               product.getCategories().add(cat);
           }

           product = productRepository.save(product);
           return new ProductDTO(product);
       }
       catch (EntityNotFoundException e)
       {
            throw new ResourceNotFoundException("Id não encontrado: "+id);
       }
    }


    private void copyDtoToEntity(ProductDTO productDTO, Product product)
    {
        product.setName(productDTO.getName());
        product.setDate(productDTO.getDate());
        product.setDescription(productDTO.getDescription());
        product.setImgUrl(productDTO.getImgUrl());
        product.setPrice(productDTO.getPrice());

        product.getCategories().clear();

        for(CategoryDTO catDTO : productDTO.getCategories())
        {
            Category category = categoryRepository.getOne(catDTO.getId());
            product.getCategories().add(category);
        }
    }

    public void delete(Long id)
    {
        try
        {
            productRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e)
        {
            throw new ResourceNotFoundException("Id não encontrado: "+id);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new DataBaseException("Violação de integridade!");
        }
    }
}
