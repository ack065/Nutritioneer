-- =====================================================================
--  NUTRITIONEER | 00 - RESET QUERY
-- =====================================================================

DROP TABLE IF EXISTS intake_planner_save_to_list_post   CASCADE;
DROP TABLE IF EXISTS grocery_list_single_add_ingredient CASCADE;
DROP TABLE IF EXISTS grocery_list_bulk_add_ingredient   CASCADE;
DROP TABLE IF EXISTS recipe_contains_restriction        CASCADE;
DROP TABLE IF EXISTS ingredient_contains_nutrient       CASCADE;
DROP TABLE IF EXISTS recipe_contains_ingredient         CASCADE;
DROP TABLE IF EXISTS biometrics                         CASCADE;
DROP TABLE IF EXISTS intake_planner                     CASCADE;
DROP TABLE IF EXISTS grocery_list                       CASCADE;
DROP TABLE IF EXISTS comment                            CASCADE;
DROP TABLE IF EXISTS post                               CASCADE;
DROP TABLE IF EXISTS recipe                             CASCADE;
DROP TABLE IF EXISTS restriction                        CASCADE;
DROP TABLE IF EXISTS nutrient                           CASCADE;
DROP TABLE IF EXISTS ingredient                         CASCADE;
DROP TABLE IF EXISTS "user"                             CASCADE;

DROP TYPE  IF EXISTS post_status      CASCADE;
DROP TYPE  IF EXISTS restriction_type CASCADE;
DROP TYPE  IF EXISTS nutrient_type    CASCADE;
DROP TYPE  IF EXISTS nutrient_unit    CASCADE;
DROP TYPE  IF EXISTS ingredient_type  CASCADE;
DROP TYPE  IF EXISTS user_role        CASCADE;
