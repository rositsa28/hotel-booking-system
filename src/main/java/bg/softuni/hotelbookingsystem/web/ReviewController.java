package bg.softuni.hotelbookingsystem.web;

import bg.softuni.hotelbookingsystem.review.model.Review;
import bg.softuni.hotelbookingsystem.review.service.ReviewService;
import bg.softuni.hotelbookingsystem.security.AuthenticationDetails;
import bg.softuni.hotelbookingsystem.web.dto.ReviewRequest;
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
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;


    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/hotel/{hotelId}/form")
    public ModelAndView showReviewForm(@PathVariable UUID hotelId) {
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setHotelId(hotelId);
        return new ModelAndView("review-form").addObject("reviewRequest", reviewRequest);
    }

    @GetMapping("/hotel/{hotelId}")
    public ModelAndView getReviewsForHotel() {
        List<Review> reviews = reviewService.getAll();
        return new ModelAndView("hotel-reviews").addObject("reviews", reviews);
    }

    @PostMapping("/submit")
    public ModelAndView submitReview(@Valid @ModelAttribute ReviewRequest reviewRequest,
                                     BindingResult result,
                                     @AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        if (result.hasErrors()) {
            return new ModelAndView("review-form").addObject("reviewRequest", reviewRequest);
        }

        UUID userId = authenticationDetails.getUserId();


        reviewService.submitReview(reviewRequest, userId);

        return new ModelAndView("redirect:/hotels/" + reviewRequest.getHotelId());
    }
}
