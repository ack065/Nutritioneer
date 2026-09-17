package mk.finki.nutritioneer.repo;

import mk.finki.nutritioneer.domain.GroceryList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroceryListRepository extends JpaRepository<GroceryList, Long> {

    List<GroceryList> findByOwnerEmailOrderByDateTimeDesc(String email);

    /** bulk-added recipes plus individually added items. */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
            UPDATE grocery_list g
            SET kcal = ROUND(
                  COALESCE((SELECT SUM(r.kcal_sum)
                            FROM grocery_list_bulk_add_ingredient b
                            JOIN recipe r ON r.id = b.recipe_id
                            WHERE b.list_id = g.id), 0)
                + COALESCE((SELECT SUM(i.kcal * s.buy_quantity / 100.0)
                            FROM grocery_list_single_add_ingredient s
                            JOIN ingredient i ON i.name = s.ingredient_name
                            WHERE s.list_id = g.id), 0)
                , 2)
            WHERE g.id = :listId
            """, nativeQuery = true)
    void recalculateKcal(@Param("listId") Long listId);

    /**
     * The consolidated shopping list: everything pulled in through a recipe,
     * unioned with everything added by hand, summed per ingredient.
     */
    @Query(value = """
            SELECT i.type::text AS "type", i.name AS "name", SUM(x.quantity) AS "totalGrams"
            FROM (
                SELECT rci.ingredient_name AS name, rci.ingredient_quantity AS quantity
                FROM grocery_list_bulk_add_ingredient b
                JOIN recipe_contains_ingredient rci ON rci.recipe_id = b.recipe_id
                WHERE b.list_id = :listId
                UNION ALL
                SELECT s.ingredient_name, s.buy_quantity
                FROM grocery_list_single_add_ingredient s
                WHERE s.list_id = :listId
            ) AS x
            JOIN ingredient i ON i.name = x.name
            GROUP BY i.type, i.name
            ORDER BY i.type, i.name
            """, nativeQuery = true)
    List<ShoppingRow> consolidate(@Param("listId") Long listId);

    interface ShoppingRow {
        String getType();
        String getName();
        java.math.BigDecimal getTotalGrams();
    }
}
