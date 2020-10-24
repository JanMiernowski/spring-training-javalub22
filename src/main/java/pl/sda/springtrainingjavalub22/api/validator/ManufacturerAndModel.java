package pl.sda.springtrainingjavalub22.api.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Constraint(validatedBy = ManufacturerAndModelValidator.class)
public @interface ManufacturerAndModel {
    String message() default "Car not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
