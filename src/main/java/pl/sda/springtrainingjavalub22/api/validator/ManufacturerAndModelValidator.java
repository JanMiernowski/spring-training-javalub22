package pl.sda.springtrainingjavalub22.api.validator;

import lombok.AllArgsConstructor;
import pl.sda.springtrainingjavalub22.domain.car.Car;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ManufacturerAndModelValidator implements ConstraintValidator<ManufacturerAndModel, Car> {
    private List<CarInformation> carDictionary = new ArrayList<>();
    @Override
    public void initialize(ManufacturerAndModel constraintAnnotation) {
        carDictionary.add(new CarInformation("VW", "Golf"));
        carDictionary.add(new CarInformation("VW", "Passat"));
        carDictionary.add(new CarInformation("Opel", "Insignia"));
        carDictionary.add(new CarInformation("Ford", "Focus"));
        carDictionary.add(new CarInformation("Fiat", "126p"));
    }
    @Override
    public boolean isValid(Car car, ConstraintValidatorContext constraintValidatorContext) {
        return carDictionary.stream().anyMatch(dict -> dict.equals(car.getManufacturer(), car.getModel()));
    }
    @AllArgsConstructor
    static class CarInformation {
        private String manufacturer;
        private String model;

        public boolean equals(String manufacturer, String model) {
            return this.manufacturer.equalsIgnoreCase(manufacturer) &&
                    this.model.equalsIgnoreCase(model);
        }
    }
}
