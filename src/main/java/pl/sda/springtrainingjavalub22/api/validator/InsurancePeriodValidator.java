package pl.sda.springtrainingjavalub22.api.validator;

import pl.sda.springtrainingjavalub22.domain.car.Car;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InsurancePeriodValidator implements ConstraintValidator<InsurancePeriod, Car> {
    @Override
    public void initialize(InsurancePeriod constraintAnnotation) { }

    @Override
    public boolean isValid(Car car, ConstraintValidatorContext constraintValidatorContext) {
        return car.getInsuredFrom().isBefore(car.getInsuredTo());
    }
}
