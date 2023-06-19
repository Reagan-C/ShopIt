package com.reagan.shopIt.model.dto;

import com.reagan.shopIt.annotations.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;

    @NotBlank(message = "FirstName is required")
    @NotNull(message = "Password is not null")
    private String firstName;

    @NotBlank(message = "LastName is required")
    @NotNull(message = "Password is not null")
    private String lastName;

    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "Password is required")
    @NotNull(message = "Password is not null")
    @Size(max = 15, min = 6)
    private String password;

    @Email(message = "Please enter a valid email address")
    private String email;

    @PhoneNumber(message = "Invalid phone number")
    private String phoneNumber;

    // Other fields and validations
}

