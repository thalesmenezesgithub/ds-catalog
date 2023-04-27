package com.sistema.dscatalog.resources;

import com.sistema.dscatalog.dto.CategoryDTO;
import com.sistema.dscatalog.entities.Category;
import com.sistema.dscatalog.services.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource
{
    @Autowired
    private CategoryService categoryService;

    /*
     * Insere Categoria
     */
    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody CategoryDTO categoryDTO)
    {
        var category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(category));
    }

    /*
     * Atualiza Categoria
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO)
    {
        categoryDTO = categoryService.update(id, categoryDTO);

        return ResponseEntity.ok().body(categoryDTO);
    }

    /*
     * Listar Todas Categorias
     */
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll()
    {
        List<CategoryDTO> list = categoryService.findAll();

        return ResponseEntity.ok().body(list);
    }

    /*
     * Pesquisa por id
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id)
    {
        CategoryDTO categoryDTO = categoryService.findById(id);

        return ResponseEntity.ok().body(categoryDTO);
    }


}
