package com.brli.articlenet.validation;

import com.brli.articlenet.annotation.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {
    /**
     *
     * @param value object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return false if validation fails, true otherwise
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return "published".equals(value) || "draft".equals(value);
    }
}
