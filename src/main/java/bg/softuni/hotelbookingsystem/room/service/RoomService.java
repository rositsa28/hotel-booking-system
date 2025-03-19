package bg.softuni.hotelbookingsystem.room.service;

import bg.softuni.hotelbookingsystem.room.model.Room;
import bg.softuni.hotelbookingsystem.room.model.RoomType;
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


    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room addRoom(RoomRequest roomRequest) {

        Room room = new Room();
        room.setRoomType(roomRequest.getRoomType());
        room.setPrice(roomRequest.getPrice());
        room.setAvailable(true);
        return roomRepository.save(room);
    }

    public void deleteRoom(UUID id) {
        roomRepository.deleteById(id);
    }

    public void save(Room room) {
        roomRepository.save(room);
    }


    public Room getById(UUID id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }


    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public void updateRoom(UUID id, RoomRequest roomRequest) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setRoomType(roomRequest.getRoomType());
        room.setPrice(roomRequest.getPrice());

        room.setAvailable(true);

        roomRepository.save(room);
    }


    public Room findById(UUID roomId) {
        return roomRepository.findById(roomId).get();
    }
    public List<Room> searchAvailableRooms(LocalDate checkIn, LocalDate checkOut, RoomType roomType) {
        if (roomType != null) {
            return roomRepository.findAvailableRoomsByDateAndType(checkIn, checkOut, roomType);
        } else {
            return roomRepository.findAvailableRoomsByDates(checkIn, checkOut);
        }
    }
}

