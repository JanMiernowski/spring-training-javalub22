package pl.sda.springtrainingjavalub22.api;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sda.springtrainingjavalub22.api.model.SearchParams;
import pl.sda.springtrainingjavalub22.api.validator.Vin;
import pl.sda.springtrainingjavalub22.domain.car.Car;
import pl.sda.springtrainingjavalub22.domain.car.CarService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/car")
public class CarApi {

    private final CarService carService;

    public CarApi(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getAll() {
        return carService.getAll();
    }

    @PostMapping("/search/{carId}")
    public List<Car> getOne(@RequestBody SearchParams searchParams) {
        return carService.getAll();
    }

    @GetMapping("/id/{carId}")
    public ResponseEntity<Car> getOne(@PathVariable Long carId) {
        return carService.getCarById(carId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createCar(@RequestBody @Valid @Vin Car car, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errors);
        }
        carService.create(car);
        return ResponseEntity.status(201).build();
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
