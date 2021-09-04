package com.example.recipes.validation;

import org.passay.AllowedRegexRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        final PasswordValidator validator = new PasswordValidator(Arrays.asList(new AllowedRegexRule(".+@.+\\..+")));
        final RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(String.join("\n", validator.getMessages(result))).addConstraintViolation();
        return false;
    }

    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }
}
