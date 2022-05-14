package pl.sda.springtrainingjavalub22.api.training;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewCar {
    @NotEmpty
    private String manufacturer;
    @NotNull
    private String model;
    @NotNull
    @Positive
    private Integer yearOfProduction;
    @NotEmpty
    private List<String> options;
}