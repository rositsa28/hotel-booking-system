package bg.softuni.hotelbookingsystem.web.dto;

import bg.softuni.hotelbookingsystem.room.model.RoomType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchRequest {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    @FutureOrPresent
    private LocalDate checkIn;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    @Future
    private LocalDate checkOut;

    private RoomType roomType;
}