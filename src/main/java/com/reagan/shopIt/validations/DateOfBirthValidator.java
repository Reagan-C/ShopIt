package com.reagan.shopIt.validations;

import com.reagan.shopIt.annotations.DateOfBirth;
import com.reagan.shopIt.model.dto.userdto.SignUpDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.*;
import java.util.Date;

public class DateOfBirthValidator implements ConstraintValidator<DateOfBirth, Date> {

    @Override
    public void initialize(DateOfBirth constraintAnnotation) {
//        init info if any
    }

    @Override
    public boolean isValid(Date dateOfBirth, ConstraintValidatorContext constraintValidatorContext) {
        if (dateOfBirth == null) {
            return false;
        }
        Instant instant = dateOfBirth.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        LocalDate dob = zonedDateTime.toLocalDate();
        Period period = Period.between(dob, LocalDate.now());
        System.out.println(period.getYears());
        return  period.getYears() >= 18;
    }
}
