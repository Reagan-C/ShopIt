package com.reagan.shopIt.model.dto.cartdto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderCartItemsDTO {

    @NotBlank(message = "{user.email.valid}")
    private String emailAddress;

    @NotBlank(message = "{product.name.notBlank}")
    private  String name;
}
