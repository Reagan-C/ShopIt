package com.reagan.shopIt.model.dto.userdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ConfirmOrderDTO {

    @JsonProperty("otp")
    @NotBlank(message = "{user.otp.notBlank}")
    private String token;

}
