

UPDATE recipe r --FIX: Join on ID not Name
SET kcal_sum = ROUND(COALESCE((
        SELECT SUM(i.kcal * rci.ingredient_quantity / 100.0)
        FROM recipe_contains_ingredient rci
        JOIN ingredient i ON i.id = rci.ingredient_id
        WHERE rci.recipe_id = r.id
    ), 0), 2);

UPDATE grocery_list g --FIX: Join on ID not Name
SET kcal = ROUND(
      COALESCE((SELECT SUM(r.kcal_sum)
                FROM grocery_list_bulk_add_ingredient b
                JOIN recipe r ON r.id = b.recipe_id
                WHERE b.list_id = g.id), 0)
    + COALESCE((SELECT SUM(i.kcal * s.buy_quantity / 100.0)
                FROM grocery_list_single_add_ingredient s
                JOIN ingredient i ON i.id = s.ingredient_id
                WHERE s.list_id = g.id), 0)
    , 2);

UPDATE intake_planner ip
SET kcal = ROUND((
        SELECT SUM(r.kcal_sum / r.servings)   
        FROM intake_planner_save_to_list_post sp
        JOIN post   p ON p.id = sp.post_id
        JOIN recipe r ON r.id = p.recipe_id
        WHERE sp.planner_id = ip.id
    ), 2)
WHERE EXISTS (SELECT 1
              FROM intake_planner_save_to_list_post sp
              JOIN post p ON p.id = sp.post_id
              WHERE sp.planner_id = ip.id AND p.recipe_id IS NOT NULL);
