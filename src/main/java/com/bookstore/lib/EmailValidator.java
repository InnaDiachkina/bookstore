package com.bookstore.lib;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final String EMAIL_VALIDATION_REGEX = "^(.+)@(.+)$";

    @Override
    public boolean isValid(String field, ConstraintValidatorContext context) {
        return field != null && field.length() >= 5 && field.length() <= 25
                && field.matches(EMAIL_VALIDATION_REGEX);
    }
}
