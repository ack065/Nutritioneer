package mk.finki.nutritioneer.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public final class RecipeDtos {

    private RecipeDtos() {
    }

    public record IngredientLine(
            @NotBlank String ingredientName,
            @NotNull @Positive BigDecimal quantity) {
    }

    public record CreateRecipeRequest(
            @NotBlank String name,
            @NotBlank String guide,
            @NotNull @Positive BigDecimal servings,
            @NotEmpty @Valid List<IngredientLine> ingredients,
            List<Long> restrictionIds) {
    }

    public record IngredientLineView(
            String ingredientName,
            BigDecimal quantity,
            BigDecimal kcalPer100g,
            BigDecimal kcalContributed) {
    }

    public record RecipeView(
            Long id,
            String name,
            String guide,
            BigDecimal kcalSum,
            BigDecimal servings,
            BigDecimal kcalPerServing,
            String ownerUsername,
            List<IngredientLineView> ingredients,
            List<String> restrictions) {
    }

    public record RecipeSummary(
            Long id,
            String name,
            BigDecimal kcalPerServing,
            String ownerUsername) {
    }

    public record NutrientDensityView(
            String recipe,
            BigDecimal kcalPerServing,
            BigDecimal amountPerServing,
            BigDecimal amountPer100Kcal) {
    }
}
