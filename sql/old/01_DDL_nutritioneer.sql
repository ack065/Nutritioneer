-- ---------------------------------------------------------------------
-- Првобитни queries
-- ---------------------------------------------------------------------
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



CREATE TYPE user_role AS ENUM (
    'user',
    'administrator',
    'trainer'
);

CREATE TYPE ingredient_type AS ENUM (
    'dairy',
    'meat',
    'fish',
    'herb',
    'carb',
    'vegetable',
    'fruit',
    'legume',
    'nut',
    'seed',
    'oil',
    'spice',
    'beverage',
    'other'
);

CREATE TYPE nutrient_unit AS ENUM (
    'milligram',
    'gram',
    'microgram'
);

CREATE TYPE nutrient_type AS ENUM (
    'macro',
    'micro'
);

CREATE TYPE restriction_type AS ENUM (
    'vegan',
    'vegetarian',
    'halal',
    'kosher',
    'pescatarian',
    'allergen',
    'keto',
    'paleo',
    'gluten_free',
    'lactose_free',
    'diabetic',
    'low_sodium',
    'other'
);

CREATE TYPE post_status AS ENUM (
    'published',
    'draft',
    'archived'
);



CREATE TABLE "user" (
    email       TEXT        PRIMARY KEY,
    username    TEXT        NOT NULL,
    password    TEXT        NOT NULL,        -- BCrypt hash, never plaintext
    role        user_role   NOT NULL DEFAULT 'user',

    CONSTRAINT uq_user_username     UNIQUE (username),
    CONSTRAINT ck_user_email_format CHECK  (email LIKE '%_@_%.__%'),
    CONSTRAINT ck_user_username_len CHECK  (length(username) BETWEEN 3 AND 40),
    CONSTRAINT ck_user_password_len CHECK  (length(password) >= 8)
);

CREATE TABLE ingredient (
    name        TEXT                NOT NULL PRIMARY KEY,
    description TEXT                NOT NULL,
    energy      NUMERIC             NOT NULL,   -- kJ  / 100 g
    kcal        NUMERIC             NOT NULL,   -- kcal / 100 g
    type        ingredient_type     NOT NULL DEFAULT 'other',

    CONSTRAINT ck_ingredient_energy CHECK (energy >= 0),
    CONSTRAINT ck_ingredient_kcal   CHECK (kcal   >= 0),
    CONSTRAINT ck_ingredient_name   CHECK (length(trim(name)) > 0)
);

CREATE TABLE nutrient (
    id          BIGINT          PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    quantity    NUMERIC         NOT NULL,   -- reference daily intake
    description TEXT            NOT NULL,   -- nutrient name, e.g. 'Protein'
    unit        nutrient_unit   NOT NULL,
    type        nutrient_type   NOT NULL,

    CONSTRAINT uq_nutrient_description UNIQUE (description),
    CONSTRAINT ck_nutrient_quantity    CHECK  (quantity > 0)
);

CREATE TABLE restriction (
    id          BIGINT              PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    description TEXT                NOT NULL,
    type        restriction_type    NOT NULL,

    CONSTRAINT uq_restriction_description UNIQUE (description)
);


CREATE TABLE recipe (
    id          BIGINT      PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name        TEXT        NOT NULL,
    guide       TEXT        NOT NULL,
    kcal_sum    NUMERIC     NOT NULL,
    servings    NUMERIC     NOT NULL DEFAULT 1,
    user_email  TEXT        NOT NULL REFERENCES "user"(email) ON DELETE CASCADE,

    CONSTRAINT uq_recipe_owner_name UNIQUE (user_email, name),
    CONSTRAINT ck_recipe_kcal_sum   CHECK  (kcal_sum >= 0),
    CONSTRAINT ck_recipe_servings   CHECK  (servings > 0)
);

CREATE TABLE post (
    id           BIGINT          PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    media        BYTEA,
    media_type   TEXT,
    media_name   TEXT,
    is_private   BOOLEAN         NOT NULL DEFAULT FALSE,
    is_favourite BOOLEAN         NOT NULL DEFAULT FALSE,
    status       post_status     NOT NULL DEFAULT 'draft',
    created_at   TIMESTAMP       NOT NULL DEFAULT NOW(),
    created_by   TEXT            NOT NULL REFERENCES "user"(email) ON DELETE CASCADE,
    recipe_id    BIGINT          REFERENCES recipe(id) ON DELETE SET NULL,

    CONSTRAINT ck_post_media_type CHECK (media IS NULL OR media_type IS NOT NULL),

    CONSTRAINT uq_post_recipe UNIQUE (recipe_id)
);

CREATE TABLE comment (
    id          BIGINT      PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    description TEXT        NOT NULL,
    created_at  TIMESTAMP   NOT NULL DEFAULT NOW(),
    post_id     BIGINT      NOT NULL REFERENCES post(id)     ON DELETE CASCADE,
    created_by  TEXT        NOT NULL REFERENCES "user"(email) ON DELETE CASCADE,

    CONSTRAINT ck_comment_not_empty CHECK (length(trim(description)) > 0)
);

CREATE TABLE grocery_list (
    id          BIGINT      PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    date_time   TIMESTAMP   NOT NULL DEFAULT NOW(),
    notes       TEXT,
    is_bought   BOOLEAN     NOT NULL DEFAULT FALSE,
    kcal        NUMERIC     NOT NULL,
    user_email  TEXT        NOT NULL REFERENCES "user"(email) ON DELETE CASCADE,

    CONSTRAINT ck_grocery_list_kcal CHECK (kcal >= 0)
);

CREATE TABLE intake_planner (
    id          BIGINT      PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    date_time   TIMESTAMP   NOT NULL DEFAULT NOW(),
    notes       TEXT,
    kcal        NUMERIC     NOT NULL,
    is_consumed BOOLEAN     NOT NULL DEFAULT FALSE,
    user_email  TEXT        NOT NULL REFERENCES "user"(email) ON DELETE CASCADE,

    CONSTRAINT ck_intake_planner_kcal CHECK (kcal >= 0)
);


CREATE TABLE biometrics (
    id               BIGINT      PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    date             DATE        NOT NULL,
    weight           NUMERIC,            -- kg
    height           NUMERIC,            -- cm
    age              NUMERIC,            -- years
    muscle_fat_ratio NUMERIC,            -- lean mass / fat mass
    user_email       TEXT        NOT NULL REFERENCES "user"(email) ON DELETE CASCADE,

    CONSTRAINT uq_biometrics_user_date UNIQUE (user_email, date),
    CONSTRAINT ck_biometrics_weight    CHECK  (weight IS NULL OR weight BETWEEN 20  AND 400),
    CONSTRAINT ck_biometrics_height    CHECK  (height IS NULL OR height BETWEEN 50  AND 260),
    CONSTRAINT ck_biometrics_age       CHECK  (age    IS NULL OR age    BETWEEN 5   AND 120),
    CONSTRAINT ck_biometrics_ratio     CHECK  (muscle_fat_ratio IS NULL OR muscle_fat_ratio > 0)
);



CREATE TABLE recipe_contains_ingredient (
    recipe_id           BIGINT      NOT NULL REFERENCES recipe(id)          ON DELETE CASCADE,
    ingredient_name     TEXT        NOT NULL REFERENCES ingredient(name)    ON DELETE CASCADE,
    ingredient_quantity NUMERIC     NOT NULL,
    PRIMARY KEY (recipe_id, ingredient_name),

    CONSTRAINT ck_rci_quantity CHECK (ingredient_quantity > 0)
);


CREATE TABLE ingredient_contains_nutrient (
    ingredient_name TEXT        NOT NULL REFERENCES ingredient(name) ON DELETE CASCADE,
    nutrient_id     BIGINT      NOT NULL REFERENCES nutrient(id)     ON DELETE CASCADE,
    quantity        NUMERIC     NOT NULL,   -- amount per 100 g, in nutrient.unit
    PRIMARY KEY (ingredient_name, nutrient_id),

    CONSTRAINT ck_icn_quantity CHECK (quantity >= 0)
);


CREATE TABLE recipe_contains_restriction (
    recipe_id       BIGINT      NOT NULL REFERENCES recipe(id)      ON DELETE CASCADE,
    restriction_id  BIGINT      NOT NULL REFERENCES restriction(id) ON DELETE CASCADE,
    PRIMARY KEY (recipe_id, restriction_id)
);


CREATE TABLE grocery_list_bulk_add_ingredient (
    list_id     BIGINT      NOT NULL REFERENCES grocery_list(id) ON DELETE CASCADE,
    recipe_id   BIGINT      NOT NULL REFERENCES recipe(id)       ON DELETE CASCADE,
    PRIMARY KEY (list_id, recipe_id)
);


CREATE TABLE grocery_list_single_add_ingredient (
    list_id             BIGINT      NOT NULL REFERENCES grocery_list(id)    ON DELETE CASCADE,
    ingredient_name     TEXT        NOT NULL REFERENCES ingredient(name)    ON DELETE CASCADE,
    buy_quantity        NUMERIC     NOT NULL,   -- grams to purchase
    PRIMARY KEY (list_id, ingredient_name),

    CONSTRAINT ck_glsai_quantity CHECK (buy_quantity > 0)
);


CREATE TABLE intake_planner_save_to_list_post (
    planner_id  BIGINT      NOT NULL REFERENCES intake_planner(id) ON DELETE CASCADE,
    post_id     BIGINT      NOT NULL REFERENCES post(id)           ON DELETE CASCADE,
    PRIMARY KEY (planner_id, post_id)
);



CREATE INDEX idx_recipe_user            ON recipe(user_email);
CREATE INDEX idx_post_user              ON post(created_by);
CREATE INDEX idx_post_recipe            ON post(recipe_id);
CREATE INDEX idx_post_feed              ON post(status, created_at DESC);
CREATE INDEX idx_comment_post           ON comment(post_id);
CREATE INDEX idx_comment_user           ON comment(created_by);
CREATE INDEX idx_grocery_list_user      ON grocery_list(user_email);
CREATE INDEX idx_intake_planner_user    ON intake_planner(user_email, date_time);
CREATE INDEX idx_biometrics_user        ON biometrics(user_email, date DESC);
CREATE INDEX idx_rci_ingredient         ON recipe_contains_ingredient(ingredient_name);
CREATE INDEX idx_icn_nutrient           ON ingredient_contains_nutrient(nutrient_id);
CREATE INDEX idx_rcr_restriction        ON recipe_contains_restriction(restriction_id);
CREATE INDEX idx_glbai_recipe           ON grocery_list_bulk_add_ingredient(recipe_id);
CREATE INDEX idx_glsai_ingredient       ON grocery_list_single_add_ingredient(ingredient_name);
CREATE INDEX idx_ipsp_post              ON intake_planner_save_to_list_post(post_id);



COMMENT ON TABLE  "user"            IS 'Every account of the system, regardless of role.';
COMMENT ON COLUMN "user".password   IS 'BCrypt hash of the password - never store plaintext.';
COMMENT ON COLUMN ingredient.energy IS 'Energy in kilojoules per 100 g of edible portion.';
COMMENT ON COLUMN ingredient.kcal   IS 'Energy in kilocalories per 100 g of edible portion.';
COMMENT ON COLUMN nutrient.quantity IS 'Reference daily intake (RDI) for an average adult.';
COMMENT ON COLUMN recipe.kcal_sum   IS 'Derived: SUM(ingredient.kcal * quantity / 100) for the WHOLE recipe.';
COMMENT ON COLUMN recipe.servings   IS 'Portions the recipe yields; kcal per portion = kcal_sum / servings.';
COMMENT ON COLUMN post.recipe_id    IS 'Recipe this post is about; NULL for a plain photo/note post.';
COMMENT ON COLUMN ingredient_contains_nutrient.quantity
                                    IS 'Amount of the nutrient per 100 g of the ingredient, in nutrient.unit.';
COMMENT ON COLUMN recipe_contains_ingredient.ingredient_quantity
                                    IS 'Grams of the ingredient used by the whole recipe.';


