package com.reagan.shopIt.validations;


import com.reagan.shopIt.annotations.EqualPassword;
import com.reagan.shopIt.model.dto.userdto.SignUpDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class EqualPasswordValidator implements ConstraintValidator<EqualPassword, SignUpDTO> {

    @Override
    public void initialize(EqualPassword password) {
//        initialization if needed
    }

    @Override
    public boolean isValid(SignUpDTO user, ConstraintValidatorContext context) {
        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();

        return password != null && password.equals(confirmPassword);
    }
}
