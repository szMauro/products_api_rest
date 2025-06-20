package com.api_products.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api_products.dtos.ProductDto;
import com.api_products.exceptions.BadRequestException;
import com.api_products.exceptions.ResourceNotFoundException;
import com.api_products.mappers.ProductMapper;
import com.api_products.models.Category;
import com.api_products.models.Product;
import com.api_products.models.ProductState;
import com.api_products.repositories.CategoryRepository;
import com.api_products.repositories.ProductRepository;
import com.api_products.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
            ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto addProduct(Long categoryId, ProductDto productDto) throws Exception {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid category id"));

        if (productDto.getPrice() == null || productDto.getPrice() <= 0) {
            throw new BadRequestException("Price must be higher than 0");
        }
        Product product = productMapper.toEntity(productDto);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProductState(Long idProduct, ProductState productState) throws Exception {
        Product productFound = productRepository.findById(idProduct)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productFound.setState(productState);
        Product updatedProduct = productRepository.save(productFound);
        return productMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long idProduct) throws Exception {
        Product productFound = productRepository.findById(idProduct)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + idProduct + " not found"));
        productRepository.delete(productFound);
    }

    @Override
    public Optional<ProductDto> getProductById(Long idProduct) {
        Optional<Product> products = productRepository.findById(idProduct);
        return products.map(productMapper::toDto);
    }

    @Override
    public Optional<ProductDto> getProductByName(String name) {
        Optional<Product> products = productRepository.findByName(name);
        return products.map(productMapper::toDto);
    }

    @Override
    public List<ProductDto> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toDto).toList();

    }

    @Override
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategory_Id(categoryId);
        return products.stream().map(productMapper::toDto).toList();
    }

    @Override
    public List<ProductDto> getProductsByState(ProductState productState) {
        List<Product> products = productRepository.findByState(productState);
        return products.stream().map(productMapper::toDto).toList();
    }

    @Override
    public ProductDto updateProduct(Long idProduct, ProductDto productDto) throws Exception {
        Product productFound = productRepository.findById(idProduct)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + idProduct + " not found"));
        productFound.setName(productDto.getName());
        productFound.setDescription(productDto.getDescription());
        productFound.setPrice(productDto.getPrice());
        productFound.setStock(productDto.getStock());
        productFound.setState(productDto.getState());

        if (productDto.getCategory() != null && productDto.getCategory().getId() != null) {
            Category category = categoryRepository.findById(productDto.getCategory().getId())
                    .orElseThrow(() -> new BadRequestException("Invalid category id"));
            productFound.setCategory(category);
        }

        Product updatedProduct = productRepository.save(productFound);

        return productMapper.toDto(updatedProduct);
    }
}
