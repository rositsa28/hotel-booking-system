package bg.softuni.hotelbookingsystem.review.service;

import bg.softuni.hotelbookingsystem.review.model.Review;
import bg.softuni.hotelbookingsystem.review.repository.ReviewRepository;
import bg.softuni.hotelbookingsystem.web.dto.ReviewRequest;
import bg.softuni.hotelbookingsystem.web.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getByUser(UUID userId) {
        return reviewRepository.findByUserId(userId);
    }

    public void deleteReview(UUID id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        reviewRepository.deleteById(id);
    }


    public void save(Review review) {
        reviewRepository.save(review);
    }

    public void submitReview(ReviewRequest reviewRequest, UUID userId) {
        Review review = DtoMapper.mapReviewRequestToReview(reviewRequest, userId);
        review.setCreatedAt(LocalDateTime.now());
        reviewRepository.save(review);

    }
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public List<Review> getTop5RecentReviews() {
        return reviewRepository.findTop5ByOrderByCreatedAtDesc();
    }

}
