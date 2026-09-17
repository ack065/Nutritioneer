package mk.finki.nutritioneer.web.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class BiometricsDtos {

    private BiometricsDtos() {
    }

    public record RecordRequest(
            @NotNull LocalDate date,
            BigDecimal weight,
            BigDecimal height,
            BigDecimal age,
            BigDecimal muscleFatRatio) {
    }

    public record MeasurementView(
            Long id,
            LocalDate date,
            BigDecimal weight,
            BigDecimal height,
            BigDecimal muscleFatRatio,
            BigDecimal bmi,
            BigDecimal changeSincePrevious) {
    }
}
