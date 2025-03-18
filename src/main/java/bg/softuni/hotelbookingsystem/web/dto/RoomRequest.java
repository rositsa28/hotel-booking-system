package bg.softuni.hotelbookingsystem.web.dto;

import bg.softuni.hotelbookingsystem.room.model.RoomType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {

    @NotNull(message = "Room type is required")
    private RoomType roomType;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    private BigDecimal price;

    @NotNull(message = "Hotel ID is required")
    private UUID hotelId;
}