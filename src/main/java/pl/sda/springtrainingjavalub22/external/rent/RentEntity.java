package pl.sda.springtrainingjavalub22.external.rent;

import pl.sda.springtrainingjavalub22.domain.user.User;
import pl.sda.springtrainingjavalub22.external.car.CarEntity;
import pl.sda.springtrainingjavalub22.external.user.UserEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "rents")
@Entity
public class RentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private CarEntity car;

    private LocalDate rentFrom;

    private LocalDate rentTo;

}
