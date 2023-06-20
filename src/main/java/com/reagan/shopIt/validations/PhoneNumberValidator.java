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
        return phoneNumber != null && phoneNumber.matches("[0-9]+")
                && phoneNumber.length() > 10 && phoneNumber.length() < 15;
    }
}
