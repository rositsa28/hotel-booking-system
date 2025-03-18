package bg.softuni.hotelbookingsystem.review.service;

import bg.softuni.hotelbookingsystem.hotel.model.Hotel;
import bg.softuni.hotelbookingsystem.hotel.service.HotelService;
import bg.softuni.hotelbookingsystem.review.model.Review;
import bg.softuni.hotelbookingsystem.review.repository.ReviewRepository;
import bg.softuni.hotelbookingsystem.web.dto.ReviewRequest;
import bg.softuni.hotelbookingsystem.web.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {


    private final ReviewRepository reviewRepository;
    private final HotelService hotelService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, HotelService hotelService) {
        this.reviewRepository = reviewRepository;
        this.hotelService = hotelService;
    }

    public List<Review> getByHotel(UUID hotelId) {
        return reviewRepository.findByHotelIdOrderByCreatedAtDesc(hotelId);
    }

    public List<Review> getByUser(UUID userId) {
        return reviewRepository.findByUserId(userId);
    }

    public void deleteReview(UUID id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        UUID hotelId = review.getHotel().getId();

        reviewRepository.deleteById(id);

        updateHotelAverageRating(hotelId);
    }


    public void save(Review review) {
        reviewRepository.save(review);
    }

    public void submitReview(ReviewRequest reviewRequest, UUID userId) {
        Review review = DtoMapper.mapReviewRequestToReview(reviewRequest, reviewRequest.getHotelId(), userId);
        review.setCreatedAt(LocalDate.now());
        reviewRepository.save(review);

        updateHotelAverageRating(reviewRequest.getHotelId());
    }

    public void updateHotelAverageRating(UUID hotelId) {
        List<Review> reviews = reviewRepository.findByHotelId(hotelId);

        double average = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        Hotel hotel = hotelService.getById(hotelId);
        hotel.setRating(average);
        hotelService.save(hotel);
    }

}
