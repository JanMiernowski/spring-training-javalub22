package pl.sda.springtrainingjavalub22.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.springtrainingjavalub22.domain.user.User;
import pl.sda.springtrainingjavalub22.domain.user.UserService;

@Controller
@RequestMapping("/mvc/register")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ModelAndView displayRegisterPage() {
        ModelAndView mav = new ModelAndView("register.html");
        mav.addObject("user", new User());
        return mav;
    }

    @PostMapping
    public String handleUserRegistration(@ModelAttribute User user) {
        userService.register(user);
        return "redirect:/mvc/login";
    }

    @GetMapping("/activation/{token}")
    public String activateAccount(@PathVariable String token) {
        if (userService.activate(token)) {
            return "activateSuccess.html";
        } else {
            return "activateFailure.html";
        }
    }
}
