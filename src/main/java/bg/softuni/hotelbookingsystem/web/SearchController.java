package bg.softuni.hotelbookingsystem.web;

import bg.softuni.hotelbookingsystem.room.model.Room;
import bg.softuni.hotelbookingsystem.search.service.SearchService;
import bg.softuni.hotelbookingsystem.web.dto.SearchRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }


    @GetMapping
    public ModelAndView showSearchForm() {
        return new ModelAndView("search/search-form")
                .addObject("searchRequest", new SearchRequest());
    }

    @PostMapping
    public ModelAndView processSearch(@Valid @ModelAttribute SearchRequest searchRequest) {
        List<Room> availableRooms = searchService.searchAvailableRooms(
                searchRequest.getCity(),
                searchRequest.getCheckIn(),
                searchRequest.getCheckOut()
        );

        return new ModelAndView("search/search-results")
                .addObject("rooms", availableRooms)
                .addObject("searchRequest", searchRequest);
    }
}


