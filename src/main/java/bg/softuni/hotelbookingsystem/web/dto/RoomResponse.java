package bg.softuni.hotelbookingsystem.web.dto;

import bg.softuni.hotelbookingsystem.room.model.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;


import java.math.BigDecimal;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    @NotNull
    private UUID id;
    @NotBlank
    private String roomNumber;
    @NotNull
    private RoomType roomType;
    @NotNull
    private BigDecimal price;
    @NotNull
    private boolean available;
    @NotBlank
    private String description;
    @URL
    private String imageUrl;
}