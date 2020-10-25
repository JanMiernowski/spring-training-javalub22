package pl.sda.springtrainingjavalub22.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchParams {
    private String manufacturer;
    private String model;
    private Integer productionFrom;
    private Integer productionTo;
    private String vin;
}
