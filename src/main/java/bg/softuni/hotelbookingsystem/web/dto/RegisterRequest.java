package bg.softuni.hotelbookingsystem.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@Getter
@Setter
public class RegisterRequest {
    public RegisterRequest() {
    }

    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @NotBlank
    @Size(min = 6, max = 20, message = "Username must be between 6 and 20 characters!")
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters!")
    private String password;

    public @NotBlank @Size(min = 6, max = 20, message = "Username must be between 6 and 20 characters!") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(min = 6, max = 20, message = "Username must be between 6 and 20 characters!") String username) {
        this.username = username;
    }

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank String email) {
        this.email = email;
    }

    public @NotBlank @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters!") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters!") String password) {
        this.password = password;
    }
}
