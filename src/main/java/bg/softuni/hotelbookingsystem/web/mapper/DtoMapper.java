package bg.softuni.hotelbookingsystem.web.mapper;

import bg.softuni.hotelbookingsystem.booking.model.Booking;
import bg.softuni.hotelbookingsystem.hotel.model.Hotel;
import bg.softuni.hotelbookingsystem.review.model.Review;
import bg.softuni.hotelbookingsystem.room.model.Room;
import bg.softuni.hotelbookingsystem.user.model.User;
import bg.softuni.hotelbookingsystem.web.dto.*;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.UUID;

@UtilityClass
public class DtoMapper {

    public static Hotel mapHotelRequestToHotel(HotelRequest hotelRequest) {
        return Hotel.builder()
                .name(hotelRequest.getName())
                .city(hotelRequest.getCity())
                .address(hotelRequest.getAddress())
                .rating(hotelRequest.getRating())
                .build();
    }

    public static Room mapRoomRequestToRoom(RoomRequest roomRequest) {
        return Room.builder()
                .roomType(roomRequest.getRoomType())
                .price(roomRequest.getPrice())
                .available(true)
                .hotel(Hotel.builder().id(roomRequest.getHotelId()).build())
                .build();
    }

    public static Booking mapBookingRequestToBooking(BookingRequest bookingRequest, UUID userId) {
        return Booking.builder()
                .room(Room.builder().id(bookingRequest.getRoomId()).build())
                .checkIn(bookingRequest.getCheckIn())
                .checkOut(bookingRequest.getCheckOut())
                .user(User.builder().id(userId).build())
                .paid(false)
                .build();
    }

    public static Review mapReviewRequestToReview(ReviewRequest dto, UUID hotelId, UUID userId) {
        return Review.builder()
                .rating(dto.getRating())
                .comment(dto.getComment())
                .createdAt(LocalDate.now())
                .hotel(Hotel.builder().id(hotelId).build())
                .user(User.builder().id(userId).build())
                .build();
    }

    public static UserEditRequest mapUserToUserEditRequest(User user) {
        return UserEditRequest.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .build();
    }

    public static HotelRequest mapHotelToHotelRequest(Hotel hotel) {
        return HotelRequest.builder()
                .name(hotel.getName())
                .city(hotel.getCity())
                .address(hotel.getAddress())
                .rating(hotel.getRating())
                .build();
    }

    public static RoomRequest mapRoomToRoomRequest(Room room) {
        return RoomRequest.builder()
                .roomType(room.getRoomType())
                .price(room.getPrice())
                .hotelId(room.getHotel().getId())
                .build();
    }
}