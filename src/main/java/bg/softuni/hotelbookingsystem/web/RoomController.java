package bg.softuni.hotelbookingsystem.web;

import bg.softuni.hotelbookingsystem.room.model.Room;
import bg.softuni.hotelbookingsystem.room.model.RoomType;
import bg.softuni.hotelbookingsystem.room.service.RoomService;
import bg.softuni.hotelbookingsystem.web.dto.RoomRequest;
import bg.softuni.hotelbookingsystem.web.dto.RoomResponse;
import bg.softuni.hotelbookingsystem.web.mapper.DtoMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ModelAndView viewAvailableRooms(@RequestParam(required = false) String type) {
        List<RoomResponse> rooms;
        if (type != null) {
            try {
                RoomType roomType = RoomType.valueOf(type.toUpperCase());
                rooms = roomService.findByRoomType(roomType);
            } catch (IllegalArgumentException e) {
                rooms = roomService.getAllRooms();
            }
        } else {
            rooms = roomService.getAllRooms();
        }
        return new ModelAndView("room/room-list").addObject("rooms", rooms);
    }
}

