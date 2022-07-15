package com.atos.offer.validation.country;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Country validator annotation
 */
@Constraint(validatedBy = CountryConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CountryConstraint {
    String message() default "Invalid Country: country must be France";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
