package mk.finki.nutritioneer.repo;

import mk.finki.nutritioneer.domain.Ingredient;
import mk.finki.nutritioneer.domain.IngredientType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {

    List<Ingredient> findByNameContainingIgnoreCaseOrderByName(String fragment);

    List<Ingredient> findByTypeOrderByName(IngredientType type);
}
