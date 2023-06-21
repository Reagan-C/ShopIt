package com.reagan.shopIt.validations;

import com.reagan.shopIt.annotations.PasswordNotOtherUserFields;
import com.reagan.shopIt.model.dto.userdto.SignUpDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordNotOtherUserFieldsValidator implements ConstraintValidator<PasswordNotOtherUserFields, SignUpDTO> {
    @Override
    public void initialize(PasswordNotOtherUserFields constraintAnnotation) {
//        initialize if needed
    }

    @Override
    public boolean isValid(SignUpDTO user, ConstraintValidatorContext constraintValidatorContext) {
        String password = user.getPassword();
        return !password.equals(user.getUsername()) &&
               !password.equals(user.getFirstName()) &&
               !password.equals(user.getLastName()) &&
               !password.equals(user.getEmailAddress());
    }
}
