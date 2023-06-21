package com.reagan.shopIt.model.dto.cartitemdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reagan.shopIt.model.domain.Products;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    @JsonProperty("cart_item_products")
    @NotBlank(message = "{cartItem.notBlank}")
    private Products product;


}
