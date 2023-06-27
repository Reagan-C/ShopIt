package com.reagan.shopIt.model.dto.cartdto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderCartItemsDTO {

    @NotBlank(message = "Please enter email address")
    private String emailAddress;

    @NotBlank(message = "Please enter name of item to remove")
    private  String name;
}
