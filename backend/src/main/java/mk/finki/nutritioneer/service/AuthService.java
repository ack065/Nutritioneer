package mk.finki.nutritioneer.service;

import lombok.RequiredArgsConstructor;
import mk.finki.nutritioneer.domain.AppUser;
import mk.finki.nutritioneer.domain.UserRole;
import mk.finki.nutritioneer.exception.ApiException;
import mk.finki.nutritioneer.repo.AppUserRepository;
import mk.finki.nutritioneer.security.JwtService;
import mk.finki.nutritioneer.web.dto.AuthDtos.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public TokenResponse register(RegisterRequest request) {
        if (users.existsById(request.email())) {
            throw ApiException.conflict("An account with that email already exists");
        }
        if (users.existsByUsername(request.username())) {
            throw ApiException.conflict("That username is already taken");
        }

        AppUser user = AppUser.builder()
                .email(request.email())
                .username(request.username())
                .password(encoder.encode(request.password()))
                .role(UserRole.user)          // self-registration never grants a privileged role
                .build();

        users.save(user);
        return token(user);
    }

    public TokenResponse login(LoginRequest request) {
        // BadCredentialsException - 401 handler.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        AppUser user = users.findById(request.email())
                .orElseThrow(() -> ApiException.notFound("Account"));
        return token(user);
    }

    private TokenResponse token(AppUser user) {
        return new TokenResponse(
                jwt.issue(user),
                "Bearer",
                jwt.getExpirationMillis(),
                user.getEmail(),
                user.getUsername(),
                user.getRole().name());
    }
}
