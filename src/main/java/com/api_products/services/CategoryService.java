package com.api_products.services;

import java.util.List;
import java.util.Optional;

import com.api_products.dtos.CategoryDto;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto) throws Exception;

    List<CategoryDto> getCategories();

    Optional<CategoryDto> getCategoryById(Long idCategory);

    CategoryDto updateCategory(Long idCategory, CategoryDto categoryDto) throws Exception;

    void deleteCategory(Long idCategory) throws Exception;
}
