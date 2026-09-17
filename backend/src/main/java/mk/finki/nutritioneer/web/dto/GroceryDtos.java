package mk.finki.nutritioneer.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public final class GroceryDtos {

    private GroceryDtos() {
    }

    public record SingleItem(
            @NotBlank String ingredientName,
            @NotNull @Positive BigDecimal buyQuantity) {
    }

    public record CreateListRequest(
            LocalDateTime dateTime,
            String notes,
            List<Long> bulkRecipeIds,
            @Valid List<SingleItem> singleItems) {
    }

    public record ListView(
            Long id,
            LocalDateTime dateTime,
            String notes,
            boolean bought,
            BigDecimal kcal,
            List<String> bulkRecipes,
            List<ShoppingLine> shoppingLines) {
    }

    public record ShoppingLine(
            String category,
            String ingredient,
            BigDecimal totalGrams) {
    }
}
