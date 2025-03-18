package bg.softuni.hotelbookingsystem.web;

import bg.softuni.hotelbookingsystem.room.model.Room;
import bg.softuni.hotelbookingsystem.room.service.RoomService;
import bg.softuni.hotelbookingsystem.web.dto.RoomRequest;
import bg.softuni.hotelbookingsystem.web.mapper.DtoMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/hotel/{hotelId}")
    public ModelAndView listRoomsByHotel(@PathVariable UUID hotelId) {
        List<Room> rooms = roomService.findByHotel(hotelId);
        ModelAndView modelAndView = new ModelAndView("rooms");
        modelAndView.addObject("rooms", rooms);
        return modelAndView;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView showCreateRoomForm() {
        return new ModelAndView("room-create").addObject("roomRequest", new RoomRequest());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView createRoom(@Valid @ModelAttribute RoomRequest roomRequest,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("room-create").addObject("roomRequest", roomRequest);
        }
        Room room = DtoMapper.mapRoomRequestToRoom(roomRequest);

        roomService.save(room);
        return new ModelAndView("redirect:/hotels");
    }
}