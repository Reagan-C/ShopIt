package com.reagan.shopIt.model.dto.onetimepassword;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TokenDTO {

    @JsonProperty("otp")
    @NotBlank(message = "Please enter otp")
    private String name;

}
