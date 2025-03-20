package bg.softuni.hotelbookingsystem.web;

import bg.softuni.hotelbookingsystem.booking.model.Booking;
import bg.softuni.hotelbookingsystem.booking.service.BookingService;
import bg.softuni.hotelbookingsystem.payment.Payment;
import bg.softuni.hotelbookingsystem.payment.PaymentRepository;
import bg.softuni.hotelbookingsystem.security.AuthenticationDetails;
import bg.softuni.hotelbookingsystem.user.service.UserService;
import bg.softuni.hotelbookingsystem.web.dto.BookingRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;
@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;
    private final PaymentRepository paymentRepository;

    @Autowired
    public BookingController(BookingService bookingService,
                             UserService userService,
                             PaymentRepository paymentRepository) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/user")
    public ModelAndView getUserBookings(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        UUID userId = authenticationDetails.getUserId();

        List<Booking> bookings = bookingService.findByUser(userId);
        List<Payment> paymentRefs = paymentRepository.findAllByBooking_User_Id(userId);

        ModelAndView modelAndView = new ModelAndView("user-bookings");
        modelAndView.addObject("bookings", bookings);
        modelAndView.addObject("paymentRefs", paymentRefs);
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createBooking(@AuthenticationPrincipal AuthenticationDetails authenticationDetails,
                                      @Valid @ModelAttribute BookingRequest bookingRequest,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("booking-form").addObject("bookingRequest", bookingRequest);
        }

        UUID userId = authenticationDetails.getUserId();
        bookingService.createBooking(bookingRequest);
        return new ModelAndView("redirect:/bookings/user");
    }

    @PostMapping("/{id}/cancel")
    public String cancelBooking(@PathVariable UUID id) {
        bookingService.cancelBooking(id);
        return "redirect:/bookings/user";
    }
}
