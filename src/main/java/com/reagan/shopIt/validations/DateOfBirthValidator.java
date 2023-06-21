package com.reagan.shopIt.validations;

import com.reagan.shopIt.annotations.DateOfBirth;
import com.reagan.shopIt.model.dto.userdto.SignUpDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.*;
import java.util.Date;

public class DateOfBirthValidator implements ConstraintValidator<DateOfBirth, SignUpDTO> {

    @Override
    public void initialize(DateOfBirth constraintAnnotation) {
//        init info if any
    }

    @Override
    public boolean isValid(SignUpDTO signUpDTO, ConstraintValidatorContext constraintValidatorContext) {
        Instant instant = signUpDTO.getDateOfBirth().toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        LocalDate dob = zonedDateTime.toLocalDate();
        Period period = Period.between(LocalDate.now(), dob);

        return  period.getYears() >= 18;
    }
}
