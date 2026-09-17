package mk.finki.nutritioneer.web.dto;

import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public final class PlannerDtos {

    private PlannerDtos() {
    }

    public record LogMealRequest(
            LocalDateTime dateTime,
            String notes,
            List<Long> postIds,
            boolean consumed,
            /* Only used when no meal is attached, e.g. "coffee and fruit". */
            @PositiveOrZero BigDecimal manualKcal) {
    }

    public record PlannerEntryView(
            Long id,
            LocalDateTime dateTime,
            String notes,
            BigDecimal kcal,
            boolean consumed,
            List<String> meals) {
    }

    public record DailyTotalView(
            LocalDate day,
            BigDecimal kcalConsumed,
            long mealsLogged) {
    }

    public record NutrientTotalView(
            String nutrient,
            String unit,
            BigDecimal total) {
    }
}
