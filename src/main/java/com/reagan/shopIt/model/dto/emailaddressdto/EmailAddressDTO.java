package com.reagan.shopIt.model.dto.emailaddressdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailAddressDTO {

    @JsonProperty("emailAddress")
    @NotBlank(message = "Please enter your email address")
    private String emailAddress;

}
