package pl.sda.springtrainingjavalub22.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloWorldApi {

    @GetMapping(path = "/hello",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String helloWorld(@RequestParam String firstName) {
        return "Hello " + firstName + " from my first api";
    }

    @PostMapping("/car")
    public String createCar(@RequestBody NewCar newCar) {
        return String.format("Car %s %s created",
                newCar.getManufacturer(), newCar.getModel());
    }
}
