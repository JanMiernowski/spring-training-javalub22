package pl.sda.springtrainingjavalub22.external.car;

import org.springframework.stereotype.Component;
import pl.sda.springtrainingjavalub22.domain.car.Car;
import pl.sda.springtrainingjavalub22.domain.car.CarRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryCarRepository implements CarRepository {

    private List<Car> cars = new ArrayList<>();
    private Long id = 0L;

    @Override
    public Optional<Car> findOne(Long id) {
        return cars.stream()
                .filter(car -> car.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean existsByVin(String vin) {
        return cars.stream().anyMatch(car -> car.getVin().equalsIgnoreCase(vin));
    }

    @Override
    public List<Car> findAll() {
        return cars;
    }

    @Override
    public void create(Car car) {
        car.setId(++id);
        cars.add(car);
    }

    @Override
    public void update(Car car) {
        delete(car.getId());
        cars.add(car);
    }

    @Override
    public void delete(Long id) {
        cars.removeIf(existingCar -> existingCar.getId().equals(id));
    }
}
