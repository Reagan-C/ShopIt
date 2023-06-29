package com.reagan.shopIt.model.dto.countryDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddCountryDTO {

    @NotBlank(message = "{country.title.notBlank}")
    private String title;

    @NotBlank(message = "{country.abbreviation.notBlank}")
    private String abbreviation;
}
