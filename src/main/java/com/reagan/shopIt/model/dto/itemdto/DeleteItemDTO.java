package com.reagan.shopIt.model.dto.itemdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteItemDTO implements Serializable {

    @JsonProperty("name")
    @NotBlank(message = "{product.name.notBlank}")
    private String itemName;

}
