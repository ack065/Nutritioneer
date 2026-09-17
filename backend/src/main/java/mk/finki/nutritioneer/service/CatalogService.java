package mk.finki.nutritioneer.service;

import lombok.RequiredArgsConstructor;
import mk.finki.nutritioneer.domain.*;
import mk.finki.nutritioneer.exception.ApiException;
import mk.finki.nutritioneer.repo.*;
import mk.finki.nutritioneer.web.dto.AdminDtos.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final IngredientRepository ingredients;
    private final NutrientRepository nutrients;
    private final RestrictionRepository restrictions;

    public List<Ingredient> searchIngredients(String query) {
        if (query == null || query.isBlank()) {
            return ingredients.findAll(org.springframework.data.domain.Sort.by("name"));
        }
        return ingredients.findByNameContainingIgnoreCaseOrderByName(query);
    }

    public List<Nutrient> allNutrients() {
        return nutrients.findAll();
    }

    public List<Restriction> allRestrictions() {
        return restrictions.findAll();
    }

    @Transactional
    public Ingredient createIngredient(CreateIngredientRequest request) {
        if (ingredients.existsById(request.name())) {
            throw ApiException.conflict("An ingredient with that name already exists");
        }
        return ingredients.save(Ingredient.builder()
                .name(request.name())
                .description(request.description())
                .energy(request.energy())
                .kcal(request.kcal())
                .type(request.type())
                .build());
    }

    @Transactional
    public Nutrient createNutrient(CreateNutrientRequest request) {
        return nutrients.save(Nutrient.builder()
                .description(request.description())
                .quantity(request.quantity())
                .unit(request.unit())
                .type(request.type())
                .build());
    }
}
