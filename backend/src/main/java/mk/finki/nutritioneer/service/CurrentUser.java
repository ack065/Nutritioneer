package mk.finki.nutritioneer.service;

import lombok.RequiredArgsConstructor;
import mk.finki.nutritioneer.domain.AppUser;
import mk.finki.nutritioneer.exception.ApiException;
import mk.finki.nutritioneer.repo.AppUserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CurrentUser {

    private final AppUserRepository users;

    public String email() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw ApiException.forbidden("Not authenticated");
        }
        return authentication.getName();
    }

    public AppUser get() {
        return users.findById(email())
                .orElseThrow(() -> ApiException.notFound("Account"));
    }
}
