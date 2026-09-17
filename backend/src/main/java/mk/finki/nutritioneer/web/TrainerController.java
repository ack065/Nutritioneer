package mk.finki.nutritioneer.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mk.finki.nutritioneer.service.BiometricsService;
import mk.finki.nutritioneer.service.PlannerService;
import mk.finki.nutritioneer.service.RecipeService;
import mk.finki.nutritioneer.web.dto.BiometricsDtos.MeasurementView;
import mk.finki.nutritioneer.web.dto.PlannerDtos.DailyTotalView;
import mk.finki.nutritioneer.web.dto.PlannerDtos.NutrientTotalView;
import mk.finki.nutritioneer.web.dto.RecipeDtos.NutrientDensityView;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/trainer")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('TRAINER','ADMINISTRATOR')")
@Tag(name = "Trainer", description = "UseCase3001 - UseCase3004")
public class TrainerController {

    private final PlannerService planner;
    private final BiometricsService biometrics;
    private final RecipeService recipes;

    @GetMapping("/clients/{email}/intake")
    @Operation(summary = "UseCase3001 - a client's daily energy intake over a period")
    public List<DailyTotalView> clientIntake(
            @PathVariable String email,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return planner.dailyTotals(email, from, to);
    }

    @GetMapping("/clients/{email}/macros")
    @Operation(summary = "UseCase3001 - macronutrients a client's meals supplied")
    public List<NutrientTotalView> clientMacros(
            @PathVariable String email,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return planner.macroTotals(email, from, to);
    }

    @GetMapping("/clients/{email}/progress")
    @Operation(summary = "UseCase3002 - a client's biometric history with BMI and deltas")
    public List<MeasurementView> clientProgress(@PathVariable String email) {
        return biometrics.history(email);
    }

    @GetMapping("/analysis/nutrient-density")
    @Operation(summary = "UseCase3004 - rank recipes by nutrient per 100 kcal")
    public List<NutrientDensityView> nutrientDensity(
            @RequestParam(defaultValue = "Protein") String nutrient,
            @RequestParam(defaultValue = "10") int limit) {
        return recipes.rankByNutrient(nutrient, limit);
    }
}
