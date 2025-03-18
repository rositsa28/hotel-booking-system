package bg.softuni.hotelbookingsystem.hotel.service;

import bg.softuni.hotelbookingsystem.hotel.model.Hotel;
import bg.softuni.hotelbookingsystem.hotel.repository.HotelRepository;
import bg.softuni.hotelbookingsystem.web.dto.HotelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    public List<Hotel> findByCity(String city) {
        return hotelRepository.findByCityContainingIgnoreCase(city);
    }

    public void deleteHotel(UUID id) {
        hotelRepository.deleteById(id);
    }

    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel getById(UUID id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));
    }

    public void updateHotel(UUID id, HotelRequest hotelRequest) {

            Hotel hotel = hotelRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Hotel not found"));

            hotel.setName(hotelRequest.getName());
            hotel.setCity(hotelRequest.getCity());
            hotel.setAddress(hotelRequest.getAddress());
            hotel.setRating(hotelRequest.getRating());

            hotelRepository.save(hotel);

    }

    public List<Hotel> findTop5RatedHotels() {
            return hotelRepository.findTop5ByOrderByRatingDesc();

    }
}
