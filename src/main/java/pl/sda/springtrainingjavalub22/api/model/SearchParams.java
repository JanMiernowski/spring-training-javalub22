package pl.sda.springtrainingjavalub22.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SearchParams {

     private String manufacturer;
     private String model;
     private Integer productionFrom;
     private Integer productionTo;
     private String vin;

}
