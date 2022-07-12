package com.atos.offer.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AgeConstraintValidator implements
        ConstraintValidator<AgeConstraint, LocalDate> {

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {

        return date.isBefore(LocalDate.now().minusYears(18));
    }
}
