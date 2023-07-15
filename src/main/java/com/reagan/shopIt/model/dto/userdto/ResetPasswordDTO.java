package com.reagan.shopIt.model.dto.userdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reagan.shopIt.annotations.EqualPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualPassword(password = "password", confirmPassword = "confirmPassword")
public class ResetPasswordDTO implements Serializable {

    @JsonProperty("email")
    @Email(message = "{user.email.valid}")
    private String emailAddress;

    @NotBlank(message = "{user.otp.notBlank}")
    private String otp;

    @JsonProperty("password")
    @NotBlank(message = "{user.password.notBlank}")
    private String password;

    @JsonProperty("confirm")
    @NotBlank(message = "{user.password.notBlank}")
    private String confirmPassword;

}
