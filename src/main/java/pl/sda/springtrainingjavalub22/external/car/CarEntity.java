package pl.sda.springtrainingjavalub22.external.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cars")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String manufacturer;
    @Column(nullable = false)
    private String model;
    private Integer yearOfProduction;
    @Column(nullable = false, unique = true, length = 20)
    private String vin;

    @OneToOne(cascade = CascadeType.ALL)
    private InsuranceEntity insurance;
}
