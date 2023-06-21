package com.reagan.shopIt.annotations;

import com.reagan.shopIt.validations.PasswordNotOtherUserFieldsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordNotOtherUserFieldsValidator.class)
@Target({ElementType.FIELD,ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordNotOtherUserFields {

    String message() default "Password should not be first name, last name, email address or username";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
