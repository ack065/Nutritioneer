package mk.finki.nutritioneer.service;

import lombok.RequiredArgsConstructor;
import mk.finki.nutritioneer.domain.*;
import mk.finki.nutritioneer.exception.ApiException;
import mk.finki.nutritioneer.repo.*;
import mk.finki.nutritioneer.web.dto.RecipeDtos.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    private final RecipeRepository recipes;
    private final IngredientRepository ingredients;
    private final RestrictionRepository restrictions;
    private final CurrentUser currentUser;

    @Transactional
    public RecipeView create(CreateRecipeRequest request) {
        AppUser owner = currentUser.get();

        Recipe recipe = Recipe.builder()
                .name(request.name())
                .guide(request.guide())
                .servings(request.servings())
                .kcalSum(BigDecimal.ZERO)   // derived below, never taken from the client
                .owner(owner)
                .build();

        for (IngredientLine line : request.ingredients()) {
            Ingredient ingredient = ingredients.findById(line.ingredientName())
                    .orElseThrow(() -> ApiException.badRequest(
                            "Unknown ingredient: " + line.ingredientName()));

            recipe.getIngredients().add(RecipeIngredient.builder()
                    .recipe(recipe)
                    .ingredient(ingredient)
                    .quantity(line.quantity())
                    .build());
        }

        if (request.restrictionIds() != null && !request.restrictionIds().isEmpty()) {
            Set<Restriction> tags = new LinkedHashSet<>(
                    restrictions.findAllById(request.restrictionIds()));
            recipe.setRestrictions(tags);
        }

        Recipe saved = recipes.save(recipe);

        // The energy of the recipe is computed by the database from the rows we
        // just wrote, using the same statement as the DML script.
        recipes.recalculateKcalSum(saved.getId());

        return view(recipes.findById(saved.getId()).orElseThrow());
    }

    @Transactional(readOnly = true)
    public RecipeView get(Long id) {
        return view(recipes.findById(id).orElseThrow(() -> ApiException.notFound("Recipe")));
    }

    @Transactional(readOnly = true)
    public List<RecipeSummary> search(String query) {
        List<Recipe> found = (query == null || query.isBlank())
                ? recipes.findAll()
                : recipes.findByNameContainingIgnoreCaseOrderByNameAsc(query);
        return found.stream().map(this::summary).toList();
    }

    @Transactional(readOnly = true)
    public List<RecipeSummary> mine() {
        return recipes.findByOwnerEmailOrderByNameAsc(currentUser.email())
                .stream().map(this::summary).toList();
    }

    @Transactional
    public void delete(Long id) {
        Recipe recipe = recipes.findById(id)
                .orElseThrow(() -> ApiException.notFound("Recipe"));
        if (!recipe.getOwner().getEmail().equals(currentUser.email())) {
            throw ApiException.forbidden("You can only delete your own recipes");
        }
        recipes.delete(recipe);
    }

    /** UseCase3004 - rank recipes by nutrient density. */
    @Transactional(readOnly = true)
    public List<NutrientDensityView> rankByNutrient(String nutrient, int limit) {
        return recipes.rankByNutrientDensity(nutrient, limit).stream()
                .map(r -> new NutrientDensityView(
                        r.getName(), r.getKcalPerServing(),
                        r.getAmountPerServing(), r.getAmountPer100Kcal()))
                .toList();
    }

    private RecipeSummary summary(Recipe r) {
        return new RecipeSummary(r.getId(), r.getName(), r.getKcalPerServing(),
                r.getOwner().getUsername());
    }

    private RecipeView view(Recipe r) {
        List<IngredientLineView> lines = r.getIngredients().stream()
                .map(ri -> new IngredientLineView(
                        ri.getIngredient().getName(),
                        ri.getQuantity(),
                        ri.getIngredient().getKcal(),
                        ri.getIngredient().getKcal()
                                .multiply(ri.getQuantity())
                                .divide(HUNDRED, 2, RoundingMode.HALF_UP)))
                .toList();

        List<String> tags = r.getRestrictions().stream()
                .map(Restriction::getDescription)
                .toList();

        return new RecipeView(r.getId(), r.getName(), r.getGuide(), r.getKcalSum(),
                r.getServings(), r.getKcalPerServing(), r.getOwner().getUsername(), lines, tags);
    }
}
