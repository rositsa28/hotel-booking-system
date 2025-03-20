package bg.softuni.hotelbookingsystem.web.dto;

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


    @Size(min = 2, max = 20, message = "First name must be 2-20 characters")
    private String firstName;


    @Size(min = 2, max = 20, message = "Last name must be 2-20 characters")
    private String lastName;

    private String phone;
}

