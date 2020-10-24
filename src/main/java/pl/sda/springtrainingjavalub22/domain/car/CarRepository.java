package pl.sda.springtrainingjavalub22.domain.car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    Optional<Car> findOne(Long id);
    boolean existsByVin(String vin);
    List<Car> findAll();
    void create(Car car);
    void update(Car car);
    void delete(Long id);
}
