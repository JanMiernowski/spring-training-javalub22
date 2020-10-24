package pl.sda.springtrainingjavalub22.domain.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sda.springtrainingjavalub22.api.validator.InsurancePeriod;
import pl.sda.springtrainingjavalub22.api.validator.ManufacturerAndModel;
import pl.sda.springtrainingjavalub22.api.validator.Vin;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@InsurancePeriod
@ManufacturerAndModel
public class Car {
    @Setter
    private Long id;
    @NotBlank(message = "Manufacturer should be not blank")
    private String manufacturer;
    @NotBlank
    private String model;
    @NotNull
    @Positive(message = "Year of production could not be negative")
    private Integer yearOfProduction;
    @Vin
    private String vin;

    private LocalDate insuredFrom;
    private LocalDate insuredTo;
}
