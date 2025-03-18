package bg.softuni.hotelbookingsystem.web;

import bg.softuni.hotelbookingsystem.hotel.model.Hotel;
import bg.softuni.hotelbookingsystem.hotel.service.HotelService;
import bg.softuni.hotelbookingsystem.user.service.UserService;
import bg.softuni.hotelbookingsystem.web.dto.HotelRequest;
import bg.softuni.hotelbookingsystem.web.mapper.DtoMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;


    @Autowired
    public HotelController(HotelService hotelService, UserService userService) {
        this.hotelService = hotelService;

    }

    @GetMapping
    public ModelAndView getAllHotels() {
        List<Hotel> hotels = hotelService.findAll();
        ModelAndView modelAndView = new ModelAndView("hotels");
        modelAndView.addObject("hotels", hotels);
        return modelAndView;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView showCreateForm(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("createHotel");
        modelAndView.addObject("hotelRequest", new HotelRequest());

        return modelAndView;

    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView createHotel(@Valid @ModelAttribute HotelRequest hotelRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("hotel-create").addObject("hotelRequest", hotelRequest);
        }

        Hotel hotel = DtoMapper.mapHotelRequestToHotel(hotelRequest);

        hotelService.save(hotel);
        return new ModelAndView("redirect:/hotels");
    }
}
