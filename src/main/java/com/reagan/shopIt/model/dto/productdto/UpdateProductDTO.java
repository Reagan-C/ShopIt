package com.reagan.shopIt.model.dto.productdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UpdateProductDTO {

    @JsonProperty("name")
    @NotBlank(message = "{product.name.notBlank}")
    private String name;

    @JsonProperty("description")
    @NotBlank(message = "{product.description.notBlank}")
    @Size(min = 25, message = "{product.description.size}")
    private String description;

    @JsonProperty("price")
    @NotBlank(message = "{product.price.notBlank}")
    private double price;

    @JsonProperty("quantity")
    @NotBlank(message = "{product.quantity.notBlank}")
    private Long quantity;

}