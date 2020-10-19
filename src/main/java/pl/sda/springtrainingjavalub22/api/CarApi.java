package pl.sda.springtrainingjavalub22.api;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sda.springtrainingjavalub22.domain.car.Car;
import pl.sda.springtrainingjavalub22.domain.car.CarService;

import java.util.List;

@RestController
@RequestMapping("/api/car")
@AllArgsConstructor
public class CarApi {

    private CarService carService;

    @GetMapping
    public List<Car> getAll() {
        return carService.getAll();
    }

    @GetMapping("/{carId}")
    public ResponseEntity<Car> getOne(@PathVariable Long carId) {
        return carService.getCarById(carId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCar(@RequestBody Car car) {
        carService.create(car);
    }

    @PutMapping
    public void updateCar(@RequestBody Car car) {
        carService.update(car);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@RequestParam Long carId) {
        carService.delete(carId);
    }
}
