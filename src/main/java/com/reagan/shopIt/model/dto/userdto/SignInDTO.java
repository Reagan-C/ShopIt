package com.reagan.shopIt.model.dto.userdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reagan.shopIt.annotations.EqualPassword;
import com.reagan.shopIt.annotations.PasswordNotOtherUserFields;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SignInDTO implements Serializable {

    @JsonProperty("email")
    @Email(message = "{user.email.valid}")
    private String emailAddress;

    @JsonProperty("password")
    @NotBlank(message = "{user.password.notBlank}")
    @Size(min = 8, message = "{user.password.size}")
    private String password;

}
