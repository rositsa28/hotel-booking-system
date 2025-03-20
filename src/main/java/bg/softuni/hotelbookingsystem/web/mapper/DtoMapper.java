package bg.softuni.hotelbookingsystem.web.mapper;

import bg.softuni.hotelbookingsystem.booking.model.Booking;
import bg.softuni.hotelbookingsystem.review.model.Review;
import bg.softuni.hotelbookingsystem.room.model.Room;
import bg.softuni.hotelbookingsystem.user.model.User;
import bg.softuni.hotelbookingsystem.web.dto.*;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.UUID;

@UtilityClass
public class DtoMapper {

    public static Room mapRoomRequestToRoom(RoomRequest roomRequest) {
        return Room.builder()
                .roomType(roomRequest.getRoomType())
                .price(roomRequest.getPrice())
                .available(true)
                .roomNumber(roomRequest.getRoomNumber())
                .roomNumber(roomRequest.getRoomNumber()).build();
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

    public static Review mapReviewRequestToReview(ReviewRequest dto, UUID userId) {
        return Review.builder()
                .rating(dto.getRating())
                .comment(dto.getComment())
                .createdAt(LocalDateTime.now())
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


    public static RoomRequest mapRoomToRoomRequest(Room room) {
        return RoomRequest.builder()
                .roomType(room.getRoomType())
                .roomNumber(room.getRoomNumber())
                .price(room.getPrice())
                .build();
    }

    public static RoomResponse mapRoomToRoomResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .price(room.getPrice())
                .available(room.isAvailable())
                .description(room.getDescription())
                .imageUrl(room.getImageUrl())
                .build();
    }
}