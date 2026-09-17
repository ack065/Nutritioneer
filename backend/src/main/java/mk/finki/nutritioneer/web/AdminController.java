package mk.finki.nutritioneer.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mk.finki.nutritioneer.domain.Ingredient;
import mk.finki.nutritioneer.domain.Nutrient;
import mk.finki.nutritioneer.domain.PostStatus;
import mk.finki.nutritioneer.service.AdminService;
import mk.finki.nutritioneer.service.CatalogService;
import mk.finki.nutritioneer.service.PostService;
import mk.finki.nutritioneer.web.dto.AdminDtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMINISTRATOR')")
@Tag(name = "Administration", description = "UseCase2001 - UseCase2004")
public class AdminController {

    private final CatalogService catalog;
    private final AdminService admin;
    private final PostService posts;

    @PostMapping("/ingredients")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "UseCase2001 - add an ingredient to the catalogue")
    public Ingredient addIngredient(@Valid @RequestBody CreateIngredientRequest request) {
        return catalog.createIngredient(request);
    }

    @PostMapping("/nutrients")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "UseCase2002 - add a nutrient and its reference daily intake")
    public Nutrient addNutrient(@Valid @RequestBody CreateNutrientRequest request) {
        return catalog.createNutrient(request);
    }

    @GetMapping("/accounts")
    @Operation(summary = "UseCase2004 - accounts with their role and activity")
    public List<AccountView> accounts() {
        return admin.accounts();
    }

    @PatchMapping("/accounts/{email}/role")
    @Operation(summary = "UseCase2004 - promote or demote an account")
    public AccountView changeRole(@PathVariable String email,
                                  @Valid @RequestBody ChangeRoleRequest request) {
        return admin.changeRole(email, request.role());
    }

    @PatchMapping("/posts/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "UseCase2003 - archive a post instead of deleting it")
    public void moderatePost(@PathVariable Long id, @RequestParam PostStatus status) {
        posts.setStatus(id, status);
    }

    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "UseCase2003 - delete an individual comment")
    public void deleteComment(@PathVariable Long id) {
        posts.deleteComment(id);
    }
}
