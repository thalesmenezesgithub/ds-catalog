package com.sistema.dscatalog.resources;

import com.sistema.dscatalog.dto.CategoryDTO;
import com.sistema.dscatalog.entities.Category;
import com.sistema.dscatalog.services.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id)
    {
        categoryService.delete(id);

        return ResponseEntity.noContent().build();
    }

    /*
     * Listar Todas Categorias
     * Paginação de 10 ordenação ASC
     */
    @GetMapping
    public ResponseEntity<Page<Category>> getAllCategories(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
    {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAllPaged(pageable));
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
