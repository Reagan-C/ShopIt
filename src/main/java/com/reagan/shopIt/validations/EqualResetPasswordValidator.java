package com.reagan.shopIt.validations;


import com.reagan.shopIt.annotations.EqualResetPassword;
import com.reagan.shopIt.model.dto.userdto.ResetPasswordDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EqualResetPasswordValidator implements ConstraintValidator<EqualResetPassword, ResetPasswordDTO> {

    @Override
    public void initialize(EqualResetPassword password) {
//        initialization if needed
    }

    @Override
    public boolean isValid(ResetPasswordDTO user, ConstraintValidatorContext context) {
        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();

        return password != null && password.equals(confirmPassword);
    }
}
