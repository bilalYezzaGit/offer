package com.atos.offer.validation.country;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Country validator: country must be France
 */
public class CountryConstraintValidator implements
        ConstraintValidator<CountryConstraint, String> {

    @Override
    public boolean isValid(String country, ConstraintValidatorContext constraintValidatorContext) {

        return "France".equals(country);
    }
}
