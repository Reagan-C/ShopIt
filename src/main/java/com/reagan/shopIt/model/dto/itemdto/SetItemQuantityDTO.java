package com.reagan.shopIt.model.dto.itemdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "{product.quantity.notNull}")
    private Integer quantity;
}
