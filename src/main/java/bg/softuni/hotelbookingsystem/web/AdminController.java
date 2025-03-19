package bg.softuni.hotelbookingsystem.web;

import bg.softuni.hotelbookingsystem.room.model.Room;
import bg.softuni.hotelbookingsystem.room.service.RoomService;
import bg.softuni.hotelbookingsystem.web.dto.RoomRequest;
import bg.softuni.hotelbookingsystem.web.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.UUID;
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final RoomService roomService;

    @Autowired
    public AdminController(RoomService roomService) {

        this.roomService = roomService;
    }


    @GetMapping("/rooms")
    public ModelAndView listRooms() {
        return new ModelAndView("admin/room-list")
                .addObject("rooms", roomService.getAllRooms());
    }

    @GetMapping("/rooms/create")
    public ModelAndView showCreateRoomForm() {
        return new ModelAndView("admin/room-form")
                .addObject("roomRequest", new RoomRequest());
    }

    @PostMapping("/rooms/create")
    public String createRoom(@ModelAttribute("roomRequest") RoomRequest roomRequest) {
        roomService.save(DtoMapper.mapRoomRequestToRoom(roomRequest));
        return "redirect:/admin/rooms";
    }

    @GetMapping("/rooms/edit/{id}")
    public ModelAndView showEditRoomForm(@PathVariable UUID id) {
        Room room = roomService.getById(id);
        RoomRequest roomRequest = DtoMapper.mapRoomToRoomRequest(room);
        return new ModelAndView("admin/room-form")
                .addObject("roomRequest", roomRequest)
                .addObject("roomId", id);
    }

    @PostMapping("/rooms/edit/{id}")
    public String updateRoom(@PathVariable UUID id,
                             @ModelAttribute("roomRequest") RoomRequest roomRequest) {
        roomService.updateRoom(id, roomRequest);
        return "redirect:/admin/rooms";
    }

    @PostMapping("/rooms/delete/{id}")
    public String deleteRoom(@PathVariable UUID id) {
        roomService.deleteRoom(id);
        return "redirect:/admin/rooms";
    }
}