package com.springboot.blog.controller;

import com.springboot.blog.payload.CategoryDTO;
import com.springboot.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(
        name = "Categories",
        description = "APIs for managing blog categories"
)
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Create category",
            description = "Create a new category"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Category created successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid input"
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied"
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.addCategory(categoryDTO);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get category by ID",
            description = "Retrieve a specific category by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Category retrieved successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Category not found"
    )
    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("id") Long id) {
        CategoryDTO savedCategory = categoryService.getCategory(id);
        return new ResponseEntity<>(savedCategory, HttpStatus.OK);
    }

    // Build Get All Categories REST API
    @Operation(
            summary = "Get all categories",
            description = "Retrieve all available categories"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Categories retrieved successfully",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CategoryDTO.class))
            )
    )
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO>  categoryList = this.categoryService.getAllCategories();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    // Build Update Category REST API
    @Operation(
            summary = "Update category",
            description = "Update an existing category by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Category updated successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid input"
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Category not found"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") Long id,
                                                      @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    // Build Delete Category REST API
    @Operation(
            summary = "Delete category",
            description = "Delete an existing category by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Category deleted successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class)
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Category not found"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<>("The category is successfully deleted!", HttpStatus.OK);
    }
}
