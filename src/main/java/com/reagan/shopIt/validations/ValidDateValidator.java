package com.reagan.shopIt.validations;

import com.google.common.base.Strings;
import com.reagan.shopIt.annotations.ValidDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidDateValidator implements ConstraintValidator<ValidDate, String> {

    private boolean isOptional;

    @Override
    public void initialize(ValidDate validDate) {
        this.isOptional = validDate.optional();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean validDate = isValidDateFormat("yyyy//MM//dd", value);
        return isOptional ? (validDate || (Strings.isNullOrEmpty(value))) : validDate;
    }

    private static boolean isValidDateFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            if (value != null) {
                date = sdf.parse(value);
                if (!value.equals(sdf.format(date))) {
                    date = null;
                }
            }
        } catch (ParseException e) {
        }
        return date != null;
    }
}
