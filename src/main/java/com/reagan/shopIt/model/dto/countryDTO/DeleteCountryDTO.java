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
public class DeleteCountryDTO {

    @NotBlank(message = "{country.title.notBlank}")
    private String title;

}
