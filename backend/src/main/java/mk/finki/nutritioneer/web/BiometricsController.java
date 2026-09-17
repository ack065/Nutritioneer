package mk.finki.nutritioneer.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mk.finki.nutritioneer.service.BiometricsService;
import mk.finki.nutritioneer.service.CurrentUser;
import mk.finki.nutritioneer.web.dto.BiometricsDtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/biometrics")
@RequiredArgsConstructor
@Tag(name = "Biometrics")
public class BiometricsController {

    private final BiometricsService biometrics;
    private final CurrentUser currentUser;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Record a measurement; one per day, a repeat updates it")
    public MeasurementView record(@Valid @RequestBody RecordRequest request) {
        return biometrics.record(request);
    }

    @GetMapping
    @Operation(summary = "History with BMI and the change since the previous measurement")
    public List<MeasurementView> mine() {
        return biometrics.history(currentUser.email());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete one of your own measurements")
    public void delete(@PathVariable Long id) {
        biometrics.delete(id);
    }
}
