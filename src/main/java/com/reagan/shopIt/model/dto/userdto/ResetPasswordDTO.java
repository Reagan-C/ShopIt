package com.reagan.shopIt.model.dto.userdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reagan.shopIt.annotations.EqualResetPassword;
import com.reagan.shopIt.model.domain.AuthToken;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResetPasswordDTO implements Serializable {

    @Email(message = "{user.email.valid}")
    private String emailAddress;

    @NotBlank(message = "Please enter Otp sent to your email address")
    private AuthToken otp;

    @JsonProperty("password")
    @NotBlank(message = "{user.password.notBlank}")
    @EqualResetPassword
    private String password;

    private String confirmPassword;

}
