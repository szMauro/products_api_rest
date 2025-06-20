package com.api_products.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.api_products.dtos.CategoryDto;
import com.api_products.exceptions.BadRequestException;
import com.api_products.exceptions.ResourceNotFoundException;
import com.api_products.mappers.CategoryMapper;
import com.api_products.models.Category;
import com.api_products.repositories.CategoryRepository;
import com.api_products.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) throws Exception {
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new BadRequestException("There's already a category with that name.");
        }
        Category category = categoryMapper.toEntity(categoryDto);
        Category newCategory = categoryRepository.save(category);
        return categoryMapper.toDto(newCategory);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDto> getCategoryById(Long idCategory) {
        Optional<Category> categories = categoryRepository.findById(idCategory);
        return categories.map(categoryMapper::toDto);
    }

    @Override
    public CategoryDto updateCategory(Long idCategory, CategoryDto categoryDto) throws Exception {
        Category categoryFound = categoryRepository.findById(idCategory)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryFound.setId(idCategory);
        categoryFound.setName(categoryDto.getName());

        Category updatedCategory = categoryRepository.save(categoryFound);

        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long idCategory) throws Exception {
        Optional<Category> category = categoryRepository.findById(idCategory);
        if (!category.isPresent()) {
            throw new ResourceNotFoundException("Category not found");
        }
        categoryRepository.deleteById(idCategory);
    }
}
