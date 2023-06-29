package com.reagan.shopIt.model.dto.itemdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reagan.shopIt.model.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddItemDTO implements Serializable {

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

    @JsonProperty("picture")
    @NotBlank(message = "{product.picture.notBlank}")
    private String picture;

    @JsonProperty("quantity")
    @NotBlank(message = "{product.quantity.notBlank}")
    private Integer quantity;

    @JsonProperty("category")
    @NotBlank(message = "{product.category.notBlank}")
    private String categoryName;

}
