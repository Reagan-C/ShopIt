package com.reagan.shopIt.model.dto.onetimepassword;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reagan.shopIt.model.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OneTimePasswordDTO {

    @JsonProperty("otp")
    @NotBlank(message = "Please enter otp")
    private String otp;

}
