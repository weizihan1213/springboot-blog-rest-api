package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CategoryDTO;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        // Map the categoryDTO to category entity object
        Category category = this.modelMapper.map(categoryDTO, Category.class);

        // Save the category record into database
        Category savedCategory = this.categoryRepository.save(category);

        return this.modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategory(Long id) {
        Category category = this.categoryRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        return this.modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categoryList = this.categoryRepository.findAll();

        return categoryList.stream().map((category -> modelMapper.map(category, CategoryDTO.class))).toList();
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {

        // Retrieve the category that needs to be updated from the database
        Category targetCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        targetCategory.setName(categoryDTO.getName());
        targetCategory.setDescription(categoryDTO.getDescription());
        targetCategory.setId(id);

        categoryRepository.save(targetCategory);

        return this.modelMapper.map(targetCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Long id) {

        // Retrieve the category that needs to be deleted from the database
        Category targetCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        categoryRepository.delete(targetCategory);
    }
}
