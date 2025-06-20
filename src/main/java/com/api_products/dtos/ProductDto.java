package com.api_products.dtos;

import com.api_products.models.Category;
import com.api_products.models.ProductState;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;

    @NotBlank(message = "Product name can't be empty")
    @Size(max = 100, message = "Name can't surpass 100 characters")
    private String name;

    @NotBlank(message = "Description can't be empty")
    @Size(max = 255, message = "Description can't surpass 255 characters")
    private String description;

    @NotNull(message = "Price can't be null")
    @Min(value = 1, message = "Price can't be lower than 0")
    private Double price;

    @NotNull(message = "Stock can't be null")
    @Min(value = 0, message = "Stock can't be lower than 0")
    private int stock;

    @NotNull(message = "Product state can't be null")
    private ProductState state;

    private Category category;
}
