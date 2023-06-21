package com.reagan.shopIt.model.dto.userdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.reagan.shopIt.annotations.DateOfBirth;
import com.reagan.shopIt.annotations.EqualPassword;
import com.reagan.shopIt.annotations.PasswordNotOtherUserFields;
import com.reagan.shopIt.annotations.PhoneNumber;
import com.reagan.shopIt.model.domain.Country;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SignUpDTO implements Serializable {

    @JsonProperty("username")
    @NotBlank(message = "{user.username.notBlank}")
    private String username;

    @JsonProperty("email")
    @Email(message = "{user.email.valid}")
    private String emailAddress;

    @JsonProperty("password")
    @NotBlank(message = "{user.password.notBlank}")
    @Size(min = 8, message = "{user.password.size}")
    @EqualPassword
    @PasswordNotOtherUserFields
    private String password;

    @Transient
    private String confirmPassword;

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

    @JsonProperty("phone_number")
    @PhoneNumber
    private String phoneNumber;

    @JsonProperty("city")
    @NotBlank(message = "{user.city.notBlank}")
    private String city;

    @JsonProperty("date_of_birth")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateOfBirth
    private Date dateOfBirth = new Date();

    @JsonProperty("state")
    @NotBlank(message = "{user.state.notBlank}")
    private String state;

    @JsonProperty("nationality_id")
    @NotBlank(message = "{user.country.notBlank}")
    private Country nationality;

}
