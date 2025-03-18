package bg.softuni.hotelbookingsystem.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEditRequest {

    @NotBlank
    @Size(min = 2, max = 20, message = "First name must be 2-20 characters")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 20, message = "Last name must be 2-20 characters")
    private String lastName;

    @NotBlank(message = "Phone is required")
    private String phone;
}

