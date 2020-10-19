package pl.sda.springtrainingjavalub22.domain.car;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {

    private CarRepository carRepository;

    public void create(Car car) {
        carRepository.create(car);
    }

    public void update(Car car) {
        carRepository.update(car);
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findOne(id);
    }

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public void delete(Long id) {
        carRepository.delete(id);
    }
}
