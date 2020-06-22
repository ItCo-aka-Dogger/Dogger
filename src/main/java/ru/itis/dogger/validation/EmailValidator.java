package ru.itis.dogger.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.itis.dogger.services.UsersService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {

    @Autowired
    private UsersService usersService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return isUnique(email, constraintValidatorContext);
    }

    private boolean isUnique(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (!usersService.checkForUniqueness(email)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Email is already in use")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
