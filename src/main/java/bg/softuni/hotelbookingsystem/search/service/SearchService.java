package bg.softuni.hotelbookingsystem.search.service;

import bg.softuni.hotelbookingsystem.room.model.Room;
import bg.softuni.hotelbookingsystem.room.service.RoomService;
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


    public List<Room> searchAvailableRooms(String city, LocalDate checkIn, LocalDate checkOut) {
        return roomService.findAvailableRoomsByCityAndDates(city, checkIn, checkOut);
    }
}