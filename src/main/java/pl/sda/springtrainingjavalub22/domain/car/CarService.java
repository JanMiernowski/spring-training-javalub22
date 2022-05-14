package pl.sda.springtrainingjavalub22.domain.car;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.springtrainingjavalub22.api.exception.AlreadyExistException;
import pl.sda.springtrainingjavalub22.api.model.SearchParams;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {

    private CarRepository carRepository;

    public void create(Car car) {
        if(carRepository.existsByVin(car.getVin())){
            throw new AlreadyExistException(String.format("Car with vin %s already exists", car.getVin()));
        }
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

    public List<Car> searchByParams(SearchParams searchParams){
        return carRepository.findByParams(searchParams);
    }
}
