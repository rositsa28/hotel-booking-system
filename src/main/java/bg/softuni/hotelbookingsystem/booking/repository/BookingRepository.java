package bg.softuni.hotelbookingsystem.booking.repository;

import bg.softuni.hotelbookingsystem.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByUserId(UUID userId);

    List<Booking> findByRoomId(UUID roomId);

    List<Booking> findByPaidFalse();



}