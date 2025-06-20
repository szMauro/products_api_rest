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

import com.api_products.dtos.ProductDto;
import com.api_products.models.Category;
import com.api_products.models.ProductState;
import com.api_products.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add/{categoryId}")
    public ResponseEntity<?> addProduct(@PathVariable Long categoryId, @Valid @RequestBody ProductDto product)
            throws Exception {
        ProductDto newProduct = productService.addProduct(categoryId, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable String name) {
        Optional<ProductDto> product = productService.getProductByName(name);
        return product.isPresent() ? ResponseEntity.ok(product.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    @GetMapping("/search/id/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        Optional<ProductDto> product = productService.getProductById(productId);
        return product.isPresent() ? ResponseEntity.ok(product.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductDto product)
            throws Exception {
        ProductDto updatedProduct = new ProductDto();
        updatedProduct.setId(productId);
        updatedProduct.setName(product.getName());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setStock(product.getStock());
        updatedProduct.setState(product.getState());
        Category category = new Category();
        category.setId(product.getCategory().getId());
        category.setName(product.getCategory().getName());
        updatedProduct.setCategory(category);

        ProductDto productDB = productService.updateProduct(updatedProduct.getId(), updatedProduct);
        return ResponseEntity.ok(productDB);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) throws Exception {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/state/{productId}")
    public ResponseEntity<?> updateProductState(@PathVariable Long productId, @RequestBody ProductState productState)
            throws Exception {
        ProductDto updatedProduct = productService.updateProductState(productId, productState);
        return ResponseEntity.ok(updatedProduct);

    }

    @GetMapping("/state/{productState}")
    public ResponseEntity<List<ProductDto>> getProductsByState(@PathVariable ProductState productState) {
        List<ProductDto> products = productService.getProductsByState(productState);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductDto> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }
}
