package bg.softuni.hotelbookingsystem.room.service;

import bg.softuni.hotelbookingsystem.room.model.Room;
import bg.softuni.hotelbookingsystem.room.model.RoomType;
import bg.softuni.hotelbookingsystem.room.repository.RoomRepository;
import bg.softuni.hotelbookingsystem.web.dto.RoomRequest;

import bg.softuni.hotelbookingsystem.web.dto.RoomResponse;
import bg.softuni.hotelbookingsystem.web.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;


    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room addRoom(RoomRequest roomRequest) {
        Room room = new Room();
        room.setRoomNumber(roomRequest.getRoomNumber());
        room.setRoomType(roomRequest.getRoomType());
        room.setPrice(roomRequest.getPrice());
        room.setAvailable(true);
        return roomRepository.save(room);
    }

    public void deleteRoom(UUID id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Room not found for deletion");
        }
        roomRepository.deleteById(id);
    }

    public void save(Room room) {
        roomRepository.save(room);
    }
    public void markRoomAsUnavailable(UUID roomId) {
        Room room = roomRepository.findById(roomId).get();
        room.setAvailable(false);
        roomRepository.save(room);
    }

    public Room findById(UUID roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }


    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(DtoMapper::mapRoomToRoomResponse)
                .collect(Collectors.toList());
    }

    public void updateRoom(UUID id, RoomRequest roomRequest) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setRoomNumber(roomRequest.getRoomNumber());
        room.setRoomType(roomRequest.getRoomType());
        room.setPrice(roomRequest.getPrice());
        room.setAvailable(true);

        roomRepository.save(room);
    }

    public List<RoomResponse> searchAvailableRooms(LocalDate checkIn, LocalDate checkOut, RoomType roomType) {
        List<Room> availableRooms = roomType != null
                ? roomRepository.findAvailableRoomsByDateAndType(checkIn, checkOut, roomType)
                : roomRepository.findAvailableRoomsByDates(checkIn, checkOut);

        return availableRooms.stream()
                .map(DtoMapper::mapRoomToRoomResponse)
                .collect(Collectors.toList());
    }

    public List<RoomResponse> findByRoomType(RoomType roomType) {
        return roomRepository.findByRoomType(roomType)
                .stream()
                .map(DtoMapper::mapRoomToRoomResponse)
                .collect(Collectors.toList());
    }

    public List<Room> getAvailableRoomsByType(RoomType roomType) {
        return roomRepository.findByRoomType(roomType)
                .stream()
                .filter(Room::isAvailable)
                .collect(Collectors.toList());
    }
}

