package bg.softuni.hotelbookingsystem.web.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequest {

    @NotBlank(message = "Hotel name is required")
    @Size(min = 3, max = 50, message = "Hotel name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "City is required")
    @Size(min = 3, max = 20, message = "City name must be between 3 and 20 characters")
    private String city;

    @NotBlank(message = "Address is required")
    private String address;

    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be at least 0")
    @DecimalMax(value = "5.0", inclusive = true, message = "Rating cannot exceed 5")
    private double rating;
}
