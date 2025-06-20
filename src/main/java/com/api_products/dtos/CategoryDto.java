package com.api_products.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;

    @NotBlank(message = "Category name can't be empty")
    @Size(max = 100, message = "Name can't surpass 100 characters")
    private String name;
}
