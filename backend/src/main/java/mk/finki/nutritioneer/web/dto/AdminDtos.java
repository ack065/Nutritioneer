package mk.finki.nutritioneer.web.dto;

import jakarta.validation.constraints.*;
import mk.finki.nutritioneer.domain.IngredientType;
import mk.finki.nutritioneer.domain.NutrientType;
import mk.finki.nutritioneer.domain.NutrientUnit;
import mk.finki.nutritioneer.domain.UserRole;

import java.math.BigDecimal;

public final class AdminDtos {

    private AdminDtos() {
    }

    public record CreateIngredientRequest(
            @NotBlank String name,
            @NotBlank String description,
            @NotNull @PositiveOrZero BigDecimal energy,
            @NotNull @PositiveOrZero BigDecimal kcal,
            @NotNull IngredientType type) {
    }

    public record CreateNutrientRequest(
            @NotBlank String description,
            @NotNull @Positive BigDecimal quantity,
            @NotNull NutrientUnit unit,
            @NotNull NutrientType type) {
    }

    public record ChangeRoleRequest(@NotNull UserRole role) {
    }

    public record AccountView(
            String email,
            String username,
            UserRole role,
            long recipes,
            long posts) {
    }
}
