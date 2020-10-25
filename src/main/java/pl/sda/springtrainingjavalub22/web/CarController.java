package pl.sda.springtrainingjavalub22.web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.springtrainingjavalub22.domain.car.Car;
import pl.sda.springtrainingjavalub22.domain.car.CarService;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("mvc/car")
@AllArgsConstructor
public class CarController {

    private CarService carService;

    @GetMapping
    ModelAndView displayCarsPage() {
        ModelAndView mav = new ModelAndView("cars.html");
        mav.addObject("cars", carService.getAll());
        mav.addObject("todayDate", LocalDate.now());

        return mav;
    }

    @GetMapping("/add")
    ModelAndView displayAddCarPage() {
        ModelAndView mav = new ModelAndView("addCar.html");
        mav.addObject("car", new Car());
        return mav;
    }

    @GetMapping("/edit/{id}")
    ModelAndView displayEditCarPage(@PathVariable Long id) {
        Optional<Car> car = carService.getCarById(id);
        ModelAndView mav = new ModelAndView();
        if (car.isPresent()) {
            mav.addObject("car", car.get());
            mav.setViewName("addCar.html");
        } else {
            mav.addObject("message", String.format("Samochód z id %d nie istnieje", id));
            mav.setViewName("error.html");
        }
        return mav;
    }

    @GetMapping("/delete/{id}")
    String handleDeleteCar(@PathVariable Long id) {
        carService.delete(id);
        return "redirect:/mvc/car";
    }

    @PostMapping("/addOrEdit")
    String handleAddCar(@ModelAttribute("car") Car car) {
        if (car.getId() != null) {
            carService.update(car);
        } else {
            carService.create(car);
        }
        return "redirect:/mvc/car";
    }
}
