package mk.finki.nutritioneer.service;

import lombok.RequiredArgsConstructor;
import mk.finki.nutritioneer.domain.AppUser;
import mk.finki.nutritioneer.domain.UserRole;
import mk.finki.nutritioneer.exception.ApiException;
import mk.finki.nutritioneer.repo.AppUserRepository;
import mk.finki.nutritioneer.repo.PostRepository;
import mk.finki.nutritioneer.repo.RecipeRepository;
import mk.finki.nutritioneer.web.dto.AdminDtos.AccountView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AppUserRepository users;
    private final RecipeRepository recipes;
    private final PostRepository posts;
    private final CurrentUser currentUser;

    @Transactional(readOnly = true)
    public List<AccountView> accounts() {
        return users.findAll().stream()
                .map(u -> new AccountView(
                        u.getEmail(),
                        u.getUsername(),
                        u.getRole(),
                        recipes.findByOwnerEmailOrderByNameAsc(u.getEmail()).size(),
                        posts.findByAuthorEmailOrderByCreatedAtDesc(u.getEmail()).size()))
                .toList();
    }

    @Transactional
    public AccountView changeRole(String email, UserRole role) {
        if (email.equals(currentUser.email())) {
            throw ApiException.badRequest("You cannot change your own role");
        }
        AppUser user = users.findById(email)
                .orElseThrow(() -> ApiException.notFound("Account"));
        user.setRole(role);
        return new AccountView(user.getEmail(), user.getUsername(), role,
                recipes.findByOwnerEmailOrderByNameAsc(email).size(),
                posts.findByAuthorEmailOrderByCreatedAtDesc(email).size());
    }
}
