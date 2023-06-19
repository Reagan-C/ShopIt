package com.reagan.shopIt.validations;

import com.reagan.shopIt.annotations.PhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        // Initialization, if needed
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        // Custom validation logic
        if (phoneNumber == null) {
            return true; // null values are considered valid
        }

        // Remove any non-digit characters
        phoneNumber = phoneNumber.replaceAll("\\D", "");

        // Check if the phone number is within the desired length range
        int maxLength = 14;
        return phoneNumber.length() <= maxLength;
    }
}
