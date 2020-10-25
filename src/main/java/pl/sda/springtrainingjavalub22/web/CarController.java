package pl.sda.springtrainingjavalub22.web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.springtrainingjavalub22.domain.car.CarService;

import java.time.LocalDate;

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

}
