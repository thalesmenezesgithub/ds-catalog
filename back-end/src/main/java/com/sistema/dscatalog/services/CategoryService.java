package com.sistema.dscatalog.services;

import com.sistema.dscatalog.dto.CategoryDTO;
import com.sistema.dscatalog.entities.Category;
import com.sistema.dscatalog.repositories.CategoryRepository;
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
public class CategoryService
{
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Category save(Category category)
    {
        return categoryRepository.save(category);
    }

    @Transactional
    public List<CategoryDTO> findAll()
    {
        List<Category> listCategory = categoryRepository.findAll();

        List<CategoryDTO> listDTO = new ArrayList<>();
        for(Category category : listCategory)
        {
            listDTO.add(new CategoryDTO(category));
        }

        return listDTO;
    }

    /*
     * Busca Paginada
     */
    @Transactional
    public Page<Category> findAllPaged(Pageable pageable)
    {
        return categoryRepository.findAll(pageable);
    }


    @Transactional
    public CategoryDTO findById(Long id)
    {
        Optional<Category> optional = categoryRepository.findById(id);

        //Se não achar o id da categoria lança uma exceção
        Category category = optional.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada!"));

        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO)
    {
       try
       {
           Category category = categoryRepository.getOne(id);
           category.setName(categoryDTO.getName());
           category = categoryRepository.save(category);

           return new CategoryDTO(category);
       }
       catch (EntityNotFoundException e)
       {
            throw new ResourceNotFoundException("Id não encontrado: "+id);
       }
    }

    public void delete(Long id)
    {
        try
        {
            categoryRepository.deleteById(id);
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
