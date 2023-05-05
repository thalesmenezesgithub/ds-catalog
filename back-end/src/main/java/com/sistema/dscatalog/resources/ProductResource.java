package com.sistema.dscatalog.resources;

import com.sistema.dscatalog.dto.ProductDTO;
import com.sistema.dscatalog.entities.Product;
import com.sistema.dscatalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductResource
{
    @Autowired
    private ProductService productService;

    /*
     * Insere novo Produto
     */
    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO productDTO)
    {
        productDTO = productService.save(productDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(productDTO);
    }


    /*
     * Busca todos Produtos Cadastrados no banco de dados
     * Realiza a paginação de 10 em 10
     */
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
    {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllPaged(pageable));
    }

    /*
     * Busca pelo id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id)
    {
        ProductDTO productDTO = productService.findById(id);

        return ResponseEntity.ok().body(productDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id,@Valid @RequestBody ProductDTO productDTO)
    {
        productDTO = productService.update(id, productDTO);

        return ResponseEntity.ok().body(productDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id)
    {
        productService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
