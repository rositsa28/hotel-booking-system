package bg.softuni.hotelbookingsystem.room.service;

import bg.softuni.hotelbookingsystem.hotel.model.Hotel;
import bg.softuni.hotelbookingsystem.hotel.service.HotelService;
import bg.softuni.hotelbookingsystem.room.model.Room;
import bg.softuni.hotelbookingsystem.room.repository.RoomRepository;
import bg.softuni.hotelbookingsystem.web.dto.RoomRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelService hotelService;

    @Autowired
    public RoomService(RoomRepository roomRepository, HotelService hotelService) {
        this.roomRepository = roomRepository;
        this.hotelService = hotelService;
    }

    public Room addRoom(RoomRequest roomRequest) {
        Hotel hotel = hotelService.getById(roomRequest.getHotelId());
        Room room = new Room();
        room.setRoomType(roomRequest.getRoomType());
        room.setPrice(roomRequest.getPrice());
        room.setHotel(hotel);
        room.setAvailable(true);
        return roomRepository.save(room);
    }

    public void deleteRoom(UUID id) {
        roomRepository.deleteById(id);
    }

    public void save(Room room) {
        roomRepository.save(room);
    }

    public List<Room> findAvailableRoomsByCityAndDates(String city, LocalDate checkIn, LocalDate checkOut) {
        return roomRepository.findAvailableRoomsByCityAndDates(city, checkIn, checkOut);
    }

    public Room getById(UUID id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public List<Room> findByHotel(UUID hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public void updateRoom(UUID id, RoomRequest roomRequest) {
        Hotel hotel = hotelService.getById(roomRequest.getHotelId());
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setRoomType(roomRequest.getRoomType());
        room.setPrice(roomRequest.getPrice());
        room.setHotel(hotel);
        room.setAvailable(true);

        roomRepository.save(room);
    }
}

