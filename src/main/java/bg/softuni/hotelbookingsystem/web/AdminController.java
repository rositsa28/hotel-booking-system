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
                .addObject("roomRequest", new RoomRequest())
                .addObject("formActionUrl", "/admin/rooms/create");
    }

    @PostMapping("/rooms/create")
    public ModelAndView createRoom(@Valid @ModelAttribute("roomRequest") RoomRequest roomRequest,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("admin/room-form")
                    .addObject("roomRequest", roomRequest)
                    .addObject("formActionUrl", "/admin/rooms/create");
        }
        roomService.save(DtoMapper.mapRoomRequestToRoom(roomRequest));
        return new ModelAndView("redirect:/admin/rooms");
    }

    @GetMapping("/rooms/edit/{id}")
    public ModelAndView showEditRoomForm(@PathVariable UUID id) {
        Room room = roomService.findById(id);
        RoomRequest roomRequest = DtoMapper.mapRoomToRoomRequest(room);
        return new ModelAndView("admin/room-form")
                .addObject("roomRequest", roomRequest)
                .addObject("roomId", id)
                .addObject("formActionUrl", "/admin/rooms/edit/" + id);
    }

    @PostMapping("/rooms/edit/{id}")
    public ModelAndView updateRoom(@PathVariable UUID id,
                                   @Valid @ModelAttribute("roomRequest") RoomRequest roomRequest,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("admin/room-form")
                    .addObject("roomRequest", roomRequest)
                    .addObject("roomId", id)
                    .addObject("formActionUrl", "/admin/rooms/edit/" + id);
        }
        roomService.updateRoom(id, roomRequest);
        return new ModelAndView("redirect:/admin/rooms");
    }

    @PostMapping("/rooms/delete/{id}")
    public String deleteRoom(@PathVariable UUID id) {
        roomService.deleteRoom(id);
        return "redirect:/admin/rooms";
    }
}
