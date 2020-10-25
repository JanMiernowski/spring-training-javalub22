package pl.sda.springtrainingjavalub22.domain.car;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
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
@Setter
@Builder
@InsurancePeriod
@ManufacturerAndModel
public class Car {
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate insuredFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate insuredTo;
}
