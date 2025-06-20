package com.api_products.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @Column(name = "productId")
    private Long id;

    @Column(name = "productName", nullable = false)
    @NotNull
    private String name;
    @Column(name = "productDescription")
    @NotNull
    private String description;
    @Column(name = "productPrice", nullable = false)
    @NotNull
    private Double price;
    @Column(name = "productStock", nullable = false)
    @NotNull
    private int stock;
    @Enumerated(EnumType.STRING)
    @Column(name = "productState", nullable = false)
    @NotNull
    private ProductState state;

    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private Category category;
}
