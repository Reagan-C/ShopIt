package com.reagan.shopIt.model.dto;

import com.reagan.shopIt.model.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productId;

    @NotEmpty(message = "Product name is required")
    private String name;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be a positive or zero value")
    private Double price;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity must be a positive or zero value")
    private Integer quantity;

    @NotNull(message = "Picture url is required")
    private String picture;

    @NotNull(message = "Category is required")
    private Long categoryId;

    // Other fields and validations
}
