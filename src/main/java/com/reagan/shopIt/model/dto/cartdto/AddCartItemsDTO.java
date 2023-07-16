package com.reagan.shopIt.model.dto.cartdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCartItemsDTO {


    @NotBlank(message = "{user.email.notBlank}")
    @Email(message = "{user.email.valid}")
    private String emailAddress;
    @JsonProperty("cart_item")
    @NotBlank(message = "{cartItem.notBlank}")
    private String itemName;

    @JsonProperty("unit")
    @NotNull(message = "{cart.unit.notNull}")
    private int unit;

}
