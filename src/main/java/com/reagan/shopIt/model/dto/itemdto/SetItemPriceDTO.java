package com.reagan.shopIt.model.dto.itemdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SetItemPriceDTO {

    @JsonProperty("name")
    @NotBlank(message = "{product.name.notBlank}")
    private String itemName;

    @JsonProperty("price")
    @NotBlank(message = "Price field should be populated")
    private double price;
}
