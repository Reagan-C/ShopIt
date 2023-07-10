package com.reagan.shopIt.model.dto.itemdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ItemCategoryDTO {

    @NotBlank(message = "{category.name.notBlank}")
    private String categoryName;
}
