package pl.sda.springtrainingjavalub22.api.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VinValidator implements ConstraintValidator<Vin, String> {
    @Override
    public void initialize(Vin constraintAnnotation) {    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length() > 3;
    }
}
