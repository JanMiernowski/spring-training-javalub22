package pl.sda.springtrainingjavalub22.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewCar {
    private String manufacturer;
    private String model;
    private Integer yearOfProduction;
    private List<String> options;
}