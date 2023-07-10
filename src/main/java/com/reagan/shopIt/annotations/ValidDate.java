package com.reagan.shopIt.annotations;

import com.reagan.shopIt.validations.ValidDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDateValidator.class)
public @interface ValidDate {

    String message() default "Please input date in the right format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean optional() default  false;
}
