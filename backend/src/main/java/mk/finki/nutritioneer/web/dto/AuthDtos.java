package mk.finki.nutritioneer.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class AuthDtos {

    private AuthDtos() {
    }

    public record RegisterRequest(
            @NotBlank @Email String email,
            @NotBlank @Size(min = 3, max = 40) String username,
            @NotBlank @Size(min = 8, message = "Password must be at least 8 characters")
            String password) {
    }

    public record LoginRequest(
            @NotBlank @Email String email,
            @NotBlank String password) {
    }

    public record TokenResponse(
            String token,
            String tokenType,
            long expiresInMs,
            String email,
            String username,
            String role) {
    }
}
