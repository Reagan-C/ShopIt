package com.reagan.shopIt.model.dto.categorydto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO implements Serializable {

    @JsonProperty("name")
    @NotBlank(message = "{category.name.notBlank}")
    private String name;

    @JsonProperty("abbreviation")
    @NotBlank(message = "{category.abbreviation.notBlank}")
    private String abbreviation;
}

