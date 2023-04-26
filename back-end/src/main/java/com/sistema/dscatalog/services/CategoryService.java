package com.sistema.dscatalog.services;

import com.sistema.dscatalog.dto.CategoryDTO;
import com.sistema.dscatalog.entities.Category;
import com.sistema.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryService
{
    @Autowired
    private CategoryRepository categoryRepository;

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
}
