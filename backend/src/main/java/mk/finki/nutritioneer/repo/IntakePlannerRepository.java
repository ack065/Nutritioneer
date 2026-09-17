package mk.finki.nutritioneer.repo;

import mk.finki.nutritioneer.domain.IntakePlanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IntakePlannerRepository extends JpaRepository<IntakePlanner, Long> {

    List<IntakePlanner> findByOwnerEmailOrderByDateTimeDesc(String email);

    List<IntakePlanner> findByOwnerEmailAndDateTimeBetweenOrderByDateTime(
            String email, LocalDateTime from, LocalDateTime to);

    /**
     * one portion of every attached meal.
     * COALESCE - an entry with no attached post would otherwise
     * write NULL into a NOT NULL column.
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
            UPDATE intake_planner ip
            SET kcal = ROUND(COALESCE((
                    SELECT SUM(r.kcal_sum / r.servings)
                    FROM intake_planner_save_to_list_post sp
                    JOIN post   p ON p.id = sp.post_id
                    JOIN recipe r ON r.id = p.recipe_id
                    WHERE sp.planner_id = ip.id
                ), 0), 2)
            WHERE ip.id = :plannerId
            """, nativeQuery = true)
    void recalculateKcal(@Param("plannerId") Long plannerId);

    /** daily totals for a client over a period. */
    @Query(value = """
            SELECT ip.date_time::date       AS "day",
                   ROUND(SUM(ip.kcal))      AS "kcalConsumed",
                   COUNT(*)                 AS "mealsLogged"
            FROM intake_planner ip
            WHERE ip.user_email = :email
              AND ip.is_consumed = TRUE
              AND ip.date_time >= :from
              AND ip.date_time <  :to
            GROUP BY 1
            ORDER BY 1
            """, nativeQuery = true)
    List<DailyIntakeRow> dailyIntake(@Param("email") String email,
                                     @Param("from") LocalDateTime from,
                                     @Param("to") LocalDateTime to);

    /** macronutrients supplied by the meals logged in a period. */
    @Query(value = """
            SELECT n.description AS "nutrient",
                   n.unit::text  AS "unit",
                   ROUND(SUM(icn.quantity * rci.ingredient_quantity / 100.0 / r.servings), 1) AS "total"
            FROM intake_planner ip
            JOIN intake_planner_save_to_list_post sp ON sp.planner_id = ip.id
            JOIN post   p ON p.id = sp.post_id
            JOIN recipe r ON r.id = p.recipe_id
            JOIN recipe_contains_ingredient rci     ON rci.recipe_id = r.id
            JOIN ingredient_contains_nutrient icn   ON icn.ingredient_name = rci.ingredient_name
            JOIN nutrient n                         ON n.id = icn.nutrient_id
            WHERE ip.user_email = :email
              AND ip.is_consumed = TRUE
              AND n.type = 'macro'
              AND ip.date_time >= :from
              AND ip.date_time <  :to
            GROUP BY n.description, n.unit
            ORDER BY n.description
            """, nativeQuery = true)
    List<NutrientTotalRow> macroTotals(@Param("email") String email,
                                       @Param("from") LocalDateTime from,
                                       @Param("to") LocalDateTime to);

    interface DailyIntakeRow {
        LocalDate getDay();
        java.math.BigDecimal getKcalConsumed();
        Long getMealsLogged();
    }

    interface NutrientTotalRow {
        String getNutrient();
        String getUnit();
        java.math.BigDecimal getTotal();
    }
}
