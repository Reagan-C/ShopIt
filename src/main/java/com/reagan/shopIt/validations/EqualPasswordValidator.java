package com.reagan.shopIt.validations;


import com.reagan.shopIt.annotations.EqualPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class EqualPasswordValidator implements ConstraintValidator<EqualPassword, Object> {

    private String passwordField;

    private String confirmPasswordField;

    @Override
    public void initialize(EqualPassword constraintAnnotation) {
        passwordField = constraintAnnotation.password();
        confirmPasswordField = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            Object password =  getFieldValue(object, passwordField);
            Object confirmPassword = getFieldValue(object, confirmPasswordField);
            return password != null && password.equals(confirmPassword);
        } catch (Exception e) {
            return false;
        }

    }

    private Object getFieldValue(Object object, String fieldName) throws Exception {
        Class<?> objectClass = object.getClass();
        Field passwordField = objectClass.getDeclaredField(fieldName);
        passwordField.setAccessible(true);
        return passwordField.get(object);
    }
}
