package com.reagan.shopIt.model.dto.itemdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UpdateItemDTO {

    @JsonProperty("item_old_name")
    @NotBlank(message = "{product.name.notBlank}")
    private String itemOldName;

    @JsonProperty("item_new_name")
    @NotBlank(message = "{product.name.notBlank}")
    private String itemNewName;

    @JsonProperty("description")
    @NotBlank(message = "{product.description.notBlank}")
    @Size(min = 25, message = "{product.description.size}")
    private String description;

    @JsonProperty("price")
    @NotNull(message = "{product.price.notBlank}")
    private double price;

}
