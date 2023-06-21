package com.reagan.shopIt.annotations;


import com.reagan.shopIt.validations.DateOfBirthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateOfBirthValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateOfBirth {

    String message() default "You should be at least 18 years to access our service";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
