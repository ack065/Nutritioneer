package mk.finki.nutritioneer.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mk.finki.nutritioneer.domain.Ingredient;
import mk.finki.nutritioneer.domain.Nutrient;
import mk.finki.nutritioneer.domain.Restriction;
import mk.finki.nutritioneer.service.CatalogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
@Tag(name = "Catalogue")
public class CatalogController {

    private final CatalogService catalog;

    @GetMapping("/ingredients")
    @Operation(summary = "Search the ingredient catalogue")
    public List<Ingredient> ingredients(@RequestParam(required = false) String q) {
        return catalog.searchIngredients(q);
    }

    @GetMapping("/nutrients")
    @Operation(summary = "List nutrients with their reference daily intake")
    public List<Nutrient> nutrients() {
        return catalog.allNutrients();
    }

    @GetMapping("/restrictions")
    @Operation(summary = "List dietary restrictions and allergen warnings")
    public List<Restriction> restrictions() {
        return catalog.allRestrictions();
    }
}
