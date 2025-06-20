package com.api_products.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api_products.dtos.CategoryDto;
import com.api_products.exceptions.ResourceNotFoundException;
import com.api_products.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) throws Exception {
        CategoryDto newCategory = categoryService.addCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<CategoryDto> categories = categoryService.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long categoryId) throws Exception {
        Optional<CategoryDto> category = categoryService.getCategoryById(categoryId);
        if (category.isPresent()) {
            return new ResponseEntity<>(category.get(), HttpStatus.OK);
        }
        throw new ResourceNotFoundException("Category not found");
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long categoryId,
            @Valid @RequestBody CategoryDto category) {
        try {
            CategoryDto updatedCategory = categoryService.updateCategory(categoryId, category);
            if (updatedCategory != null) {
                return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
            }
            throw new ResourceNotFoundException("Category not found");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
