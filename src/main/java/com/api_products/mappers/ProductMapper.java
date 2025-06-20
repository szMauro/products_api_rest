package com.api_products.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.api_products.dtos.ProductDto;
import com.api_products.models.Product;

@Component
public class ProductMapper {
    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Product toEntity(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }

    public void toEntity(ProductDto productDto, Product product) {
        modelMapper.map(productDto, product);
    }

    public ProductDto toDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }
}
