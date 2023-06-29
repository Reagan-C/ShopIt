package com.reagan.shopIt.model.dto.countryDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UpdateCountryDTO {

    @NotBlank(message = "{country.title.notBlank}")
    private String oldTitle;
    @NotBlank(message = "{country.title.notBlank}")
    private String newTitle;

    @NotBlank(message = "{country.abbreviation.notBlank}")
    private String abbreviation;
}
