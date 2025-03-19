package bg.softuni.hotelbookingsystem.web;

import bg.softuni.hotelbookingsystem.booking.service.BookingService;
import bg.softuni.hotelbookingsystem.room.model.RoomType;
import bg.softuni.hotelbookingsystem.room.service.RoomService;
import bg.softuni.hotelbookingsystem.security.AuthenticationDetails;
import bg.softuni.hotelbookingsystem.user.model.User;
import bg.softuni.hotelbookingsystem.user.service.UserService;
import bg.softuni.hotelbookingsystem.web.dto.RegisterRequest;
import bg.softuni.hotelbookingsystem.web.dto.SearchRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;


@Controller
public class IndexController {

    private final UserService userService;
   private final RoomService roomService;
    private final BookingService bookingService;

    @Autowired
    public IndexController(UserService userService, RoomService roomService, BookingService bookingService) {
        this.userService = userService;
        this.roomService = roomService;
        this.bookingService = bookingService;
    }

    @GetMapping("/")
    public String getIndexPage() {

        return "index";
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage(@RequestParam(value = "error", required = false) String errorParam) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");

        if (errorParam != null) {
            modelAndView.addObject("errorMessage", "Incorrect username or password!");
        }

        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        modelAndView.addObject("registerRequest", new RegisterRequest());

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerNewUser(@Valid RegisterRequest registerRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("register");
        }

        userService.register(registerRequest);

        return new ModelAndView("redirect:/login");
    }



    @GetMapping("/home")
    public ModelAndView getHomePage(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        UUID userId = authenticationDetails.getUserId();
        User user = userService.getById(userId);

        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("user", user);
        modelAndView.addObject("searchRequest", new SearchRequest());
        modelAndView.addObject("roomTypes", List.of(RoomType.values()));
        return modelAndView;
    }
}