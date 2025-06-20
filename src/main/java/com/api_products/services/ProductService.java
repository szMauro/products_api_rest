package com.api_products.services;

import java.util.List;
import java.util.Optional;

import com.api_products.dtos.ProductDto;
import com.api_products.models.ProductState;

public interface ProductService {
    ProductDto addProduct(Long categoryId, ProductDto product) throws Exception;

    List<ProductDto> getProducts();

    Optional<ProductDto> getProductByName(String name);

    Optional<ProductDto> getProductById(Long idProduct);

    List<ProductDto> getProductsByCategory(Long categoryId);

    ProductDto updateProduct(Long idProduct, ProductDto product) throws Exception;

    void deleteProduct(Long idProduct) throws Exception;

    ProductDto updateProductState(Long idProduct, ProductState productState) throws Exception;

    List<ProductDto> getProductsByState(ProductState productState);
}
