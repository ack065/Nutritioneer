package mk.finki.nutritioneer.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mk.finki.nutritioneer.service.RecipeService;
import mk.finki.nutritioneer.web.dto.RecipeDtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@Tag(name = "Recipes", description = "UseCase1001")
public class RecipeController {

    private final RecipeService recipes;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a recipe; the server derives its energy from the ingredients")
    public RecipeView create(@Valid @RequestBody CreateRecipeRequest request) {
        return recipes.create(request);
    }

    @GetMapping
    @Operation(summary = "Search all recipes")
    public List<RecipeSummary> search(@RequestParam(required = false) String q) {
        return recipes.search(q);
    }

    @GetMapping("/mine")
    @Operation(summary = "Recipes owned by the signed-in user")
    public List<RecipeSummary> mine() {
        return recipes.mine();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Full recipe with ingredients, tags and per-portion energy")
    public RecipeView get(@PathVariable Long id) {
        return recipes.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete one of your own recipes")
    public void delete(@PathVariable Long id) {
        recipes.delete(id);
    }
}
