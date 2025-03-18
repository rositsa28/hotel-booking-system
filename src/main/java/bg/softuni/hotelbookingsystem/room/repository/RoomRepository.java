package bg.softuni.hotelbookingsystem.room.repository;

import bg.softuni.hotelbookingsystem.room.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    @Query("""
    SELECT r FROM Room r
    WHERE r.hotel.city = :city
      AND r.available = true
      AND r.id NOT IN (
          SELECT b.room.id FROM Booking b
          WHERE (:checkIn < b.checkOut AND :checkOut > b.checkIn)
      )
""")
    List<Room> findAvailableRoomsByCityAndDates(@Param("city") String city,
                                                @Param("checkIn") LocalDate checkIn,
                                                @Param("checkOut") LocalDate checkOut);


    List<Room> findByHotelId(UUID hotelId);
}
