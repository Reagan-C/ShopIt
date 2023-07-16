package com.reagan.shopIt.model.dto.userdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordDTO {

    @JsonProperty("email_address")
    @NotBlank(message = "{user.email.notBlank}")
    @Email(message = "{user.email.valid}")
    private String emailAddress;

}
