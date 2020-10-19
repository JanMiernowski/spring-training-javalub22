package pl.sda.springtrainingjavalub22.domain.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Car {
    @Setter
    private Long id;
    private String manufacturer;
    private String model;
    private Integer yearOfProduction;
    private String vin;
}
