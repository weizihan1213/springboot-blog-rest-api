package com.springboot.blog.service;

import com.springboot.blog.payload.CategoryDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {
    CategoryDTO addCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategory(Long id);

    List<CategoryDTO> getAllCategories();

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
}
