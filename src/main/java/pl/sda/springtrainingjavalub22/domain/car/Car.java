package pl.sda.springtrainingjavalub22.domain.car;

import lombok.*;
import pl.sda.springtrainingjavalub22.api.validator.InsurancePeriod;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Car {
    @Setter
    private Long id;
    @NotBlank(message = "Manufacturer should be not blank")
    private String manufacturer;
    @NotBlank
    private String model;
    @NotNull
    @Positive(message =  "Year of production could not be negative")
    private Integer yearOfProduction;
    @NotBlank
    private String vin;

    private LocalDate insuredFrom;
    private LocalDate insuredTo;
}
