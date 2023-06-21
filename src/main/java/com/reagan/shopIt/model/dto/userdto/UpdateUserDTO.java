package com.reagan.shopIt.model.dto.userdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reagan.shopIt.model.domain.Country;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateUserDTO implements Serializable {

    @JsonProperty("first_name")
    @NotBlank(message = "{user.firstName.notBlank}")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "{user.lastName.notBlank}")
    private String lastName;

    @JsonProperty("address")
    @NotBlank(message = "{user.address.notBlank}")
    @Size(min = 10, message = "{user.address.size}")
    private String address;

    @JsonProperty("city")
    @NotBlank(message = "{user.city.notBlank}")
    private String city;

    @JsonProperty("state")
    @NotBlank(message = "{user.state.notBlank}")
    private String state;

    @JsonProperty("nationality_id")
    @NotBlank(message = "{user.country.notBlank}")
    private Country nationality;

}
