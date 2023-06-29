package com.reagan.shopIt.model.dto.itemdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SetItemQuantityDTO {

    @JsonProperty("name")
    @NotBlank(message = "{product.name.notBlank}")
    private String itemName;

    @JsonProperty("quantity")
    @NotBlank(message = "{product.quantity.notBlank}")
    private Integer quantity;
}
