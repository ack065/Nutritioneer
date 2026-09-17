package mk.finki.nutritioneer.repo;

import mk.finki.nutritioneer.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByOwnerEmailOrderByNameAsc(String email);

    List<Recipe> findByNameContainingIgnoreCaseOrderByNameAsc(String fragment);

    /**
     * Refreshes the derived column recipe.kcal_sum from the ingredient rows.
     * keeping the calculation in one place means a recipe edited through the API
     * and a recipe loaded from the seed file are computed identically.
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
            UPDATE recipe r
            SET kcal_sum = ROUND(COALESCE((
                    SELECT SUM(i.kcal * rci.ingredient_quantity / 100.0)
                    FROM recipe_contains_ingredient rci
                    JOIN ingredient i ON i.name = rci.ingredient_name
                    WHERE rci.recipe_id = r.id
                ), 0), 2)
            WHERE r.id = :recipeId
            """, nativeQuery = true)
    void recalculateKcalSum(@Param("recipeId") Long recipeId);

    /** rank recipes by how much of a nutrient they deliver per 100 kcal. */
    @Query(value = """
            SELECT r.name                                                       AS "name",
                   ROUND(r.kcal_sum / r.servings)                               AS "kcalPerServing",
                   ROUND(SUM(icn.quantity * rci.ingredient_quantity / 100.0)
                         / r.servings, 1)                                       AS "amountPerServing",
                   ROUND(SUM(icn.quantity * rci.ingredient_quantity / 100.0)
                         / NULLIF(r.kcal_sum, 0) * 100, 2)                      AS "amountPer100Kcal"
            FROM recipe r
            JOIN recipe_contains_ingredient rci   ON rci.recipe_id = r.id
            JOIN ingredient_contains_nutrient icn ON icn.ingredient_name = rci.ingredient_name
            JOIN nutrient n                       ON n.id = icn.nutrient_id
            WHERE n.description = :nutrient
            GROUP BY r.id, r.name, r.kcal_sum, r.servings
            ORDER BY "amountPer100Kcal" DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<NutrientDensityRow> rankByNutrientDensity(@Param("nutrient") String nutrient,
                                                   @Param("limit") int limit);

    interface NutrientDensityRow {
        String getName();
        java.math.BigDecimal getKcalPerServing();
        java.math.BigDecimal getAmountPerServing();
        java.math.BigDecimal getAmountPer100Kcal();
    }
}
