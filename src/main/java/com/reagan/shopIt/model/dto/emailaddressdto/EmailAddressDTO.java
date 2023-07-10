package com.reagan.shopIt.model.dto.emailaddressdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailAddressDTO {

    @JsonProperty("emailAddress")
    @Email(message = "{user.email.valid}")
    private String emailAddress;

}
