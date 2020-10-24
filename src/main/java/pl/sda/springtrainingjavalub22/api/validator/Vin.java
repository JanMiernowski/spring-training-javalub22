package pl.sda.springtrainingjavalub22.api.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VinValidator.class)
public @interface Vin {
    String message() default "Vin should not be empty and should have 10 signs";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
