package com.atos.offer.validation.age;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Age validator annotation
 */
@Constraint(validatedBy = AgeConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AgeConstraint {
    String message() default "Invalid Age: user must be +18";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
