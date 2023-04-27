package com.sistema.dscatalog.resources;

import com.sistema.dscatalog.dto.CategoryDTO;
import com.sistema.dscatalog.entities.Category;
import com.sistema.dscatalog.services.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource
{
    @Autowired
    private CategoryService categoryService;

    /*
     * Salvar
     */
    @PostMapping
    public ResponseEntity<Object> save(@RequestBody CategoryDTO categoryDTO)
    {
        var category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(category));
    }


    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll()
    {
        List<CategoryDTO> list = categoryService.findAll();

        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id)
    {
        CategoryDTO categoryDTO = categoryService.findById(id);

        return ResponseEntity.ok().body(categoryDTO);
    }


}
