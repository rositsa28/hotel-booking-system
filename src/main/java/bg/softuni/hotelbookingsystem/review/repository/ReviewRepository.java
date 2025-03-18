package bg.softuni.hotelbookingsystem.review.repository;

import bg.softuni.hotelbookingsystem.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    List<Review> findByHotelIdOrderByCreatedAtDesc(UUID hotelId);
    List<Review> findByUserId(UUID userId);

    List<Review> findByHotelId(UUID hotelId);
}
