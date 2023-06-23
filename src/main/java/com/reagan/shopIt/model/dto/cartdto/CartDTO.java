package com.reagan.shopIt.model.dto.cartdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reagan.shopIt.model.domain.Item;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    @JsonProperty("cart_item")
    @NotBlank(message = "{cartItem.notBlank}")
    private String itemName;


}
