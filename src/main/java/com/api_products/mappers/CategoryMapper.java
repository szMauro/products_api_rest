package com.api_products.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.api_products.dtos.CategoryDto;
import com.api_products.models.Category;

@Component
public class CategoryMapper {
    private final ModelMapper modelMapper;

    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Category toEntity(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

    public void toEntity(CategoryDto categoryDto, Category category) {
        modelMapper.map(categoryDto, category);
    }

    public CategoryDto toDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }
}
