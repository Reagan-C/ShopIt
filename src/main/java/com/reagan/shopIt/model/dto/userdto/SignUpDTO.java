package com.reagan.shopIt.model.dto.userdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.reagan.shopIt.annotations.DateOfBirth;
import com.reagan.shopIt.annotations.EqualPassword;
import com.reagan.shopIt.annotations.PasswordNotOtherUserFields;
import com.reagan.shopIt.annotations.PhoneNumber;
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

    @JsonProperty("email")
    @Email(message = "Please enter a valid email address")
    private String emailAddress;

    @JsonProperty("password")
    @NotBlank(message = "Password should be provided")
    @Size(min = 8, message = "Your password should be at least 8 characters in length")
    private String password;

    @Transient
    private String confirmPassword;

    @JsonProperty("first_name")
    @NotBlank(message = "First name should be provided")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "Please fill your Lastname field")
    private String lastName;

    @JsonProperty("address")
    @NotBlank(message = "Address should be inputted")
    @Size(min = 10, message = "Address size should be more than 10 characters")
    private String address;

    @JsonProperty("phone_number")
    @PhoneNumber
    private String phoneNumber;

    @JsonProperty("city")
    @NotBlank(message = "Please enter your city name")
    private String city;

    @JsonProperty("date_of_birth")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @JsonProperty("state")
    @NotBlank(message = "Please enter the name of your state")
    private String state;

    @NotBlank(message = "Please enter your country name")
    private String country;

}
