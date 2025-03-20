package bg.softuni.hotelbookingsystem.search.service;

import bg.softuni.hotelbookingsystem.room.model.RoomType;
import bg.softuni.hotelbookingsystem.room.service.RoomService;
import bg.softuni.hotelbookingsystem.web.dto.RoomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SearchService {

    private final RoomService roomService;

    @Autowired
    public SearchService(RoomService roomService) {
        this.roomService = roomService;
    }

    public List<RoomResponse> findAvailableRooms(LocalDate checkIn, LocalDate checkOut, RoomType roomType) {
        return roomService.searchAvailableRooms(checkIn, checkOut, roomType);

    }
}