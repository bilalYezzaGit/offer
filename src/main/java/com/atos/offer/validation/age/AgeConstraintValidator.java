package com.atos.offer.validation.age;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * Age validator: age must be above 18
 */
public class AgeConstraintValidator implements
        ConstraintValidator<AgeConstraint, LocalDate> {

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        return date == null || date.isBefore(LocalDate.now().minusYears(18));
    }
}
