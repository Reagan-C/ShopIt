package com.reagan.shopIt.model.dto.productdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteProductDTO implements Serializable {

    @JsonProperty("name")
    @NotBlank(message = "{product.name.notBlank}")
    private String name;

}
