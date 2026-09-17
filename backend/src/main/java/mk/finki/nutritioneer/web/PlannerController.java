package mk.finki.nutritioneer.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mk.finki.nutritioneer.service.CurrentUser;
import mk.finki.nutritioneer.service.PlannerService;
import mk.finki.nutritioneer.web.dto.PlannerDtos.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/planner")
@RequiredArgsConstructor
@Tag(name = "Intake planner", description = "UseCase1002")
public class PlannerController {

    private final PlannerService planner;
    private final CurrentUser currentUser;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Log a meal; energy is derived as one portion per attached meal")
    public PlannerEntryView log(@Valid @RequestBody LogMealRequest request) {
        return planner.log(request);
    }

    @GetMapping
    @Operation(summary = "The signed-in user's diary, newest first")
    public List<PlannerEntryView> mine() {
        return planner.mine();
    }

    @GetMapping("/daily")
    @Operation(summary = "Daily totals for the signed-in user over a period")
    public List<DailyTotalView> daily(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return planner.dailyTotals(currentUser.email(), from, to);
    }

    @GetMapping("/macros")
    @Operation(summary = "Macronutrients supplied by the meals logged in a period")
    public List<NutrientTotalView> macros(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return planner.macroTotals(currentUser.email(), from, to);
    }

    @PatchMapping("/{id}/consumed")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Flip a planned meal to consumed, or back")
    public void markConsumed(@PathVariable Long id, @RequestParam boolean value) {
        planner.markConsumed(id, value);
    }
}
