package com.api_products.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api_products.models.Product;
import com.api_products.models.ProductState;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String productName);

    Optional<Product> findById(Long idProduct);

    List<Product> findByState(ProductState productState);

    List<Product> findByCategory_Id(Long categoryId);
}
