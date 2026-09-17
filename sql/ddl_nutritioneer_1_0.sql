-- ---------------------------------------------------------------------
-- Користени queries
-- ---------------------------------------------------------------------
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
    password    TEXT        NOT NULL,        
    role        user_role   NOT NULL DEFAULT 'user',
 
    CONSTRAINT uq_user_username     UNIQUE (username),
    CONSTRAINT ck_user_email_format CHECK  (email LIKE '%_@_%.__%'),
    CONSTRAINT ck_user_username_len CHECK  (length(username) BETWEEN 3 AND 40),
    CONSTRAINT ck_user_password_len CHECK  (length(password) >= 8)
);
 
CREATE TABLE ingredient (
    name        TEXT                NOT NULL PRIMARY KEY,
    description TEXT                NOT NULL,
    energy      NUMERIC             NOT NULL,   
    kcal        NUMERIC             NOT NULL,   
    type        ingredient_type     NOT NULL DEFAULT 'other',
 
    CONSTRAINT ck_ingredient_energy CHECK (energy >= 0),
    CONSTRAINT ck_ingredient_kcal   CHECK (kcal   >= 0)
);
 
CREATE TABLE nutrient (
    id          BIGINT          PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    quantity    NUMERIC         NOT NULL,   
    description TEXT            NOT NULL,   
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
    CONSTRAINT uq_post_recipe     UNIQUE (recipe_id)
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
    weight           NUMERIC,            
    height           NUMERIC,            
    age              NUMERIC,            
    muscle_fat_ratio NUMERIC,            
    user_email       TEXT        NOT NULL REFERENCES "user"(email) ON DELETE CASCADE,
 
    CONSTRAINT uq_biometrics_user_date UNIQUE (user_email, date)
);
 
 
CREATE TABLE recipe_contains_ingredient (
    recipe_id           BIGINT      NOT NULL REFERENCES recipe(id)          ON DELETE CASCADE,
    ingredient_name     TEXT        NOT NULL REFERENCES ingredient(name)    ON DELETE CASCADE,
    ingredient_quantity NUMERIC     NOT NULL,   
    PRIMARY KEY (recipe_id, ingredient_name)
);
 
CREATE TABLE ingredient_contains_nutrient (
    ingredient_name TEXT        NOT NULL REFERENCES ingredient(name) ON DELETE CASCADE,
    nutrient_id     BIGINT      NOT NULL REFERENCES nutrient(id)     ON DELETE CASCADE,
    quantity        NUMERIC     NOT NULL,   
    PRIMARY KEY (ingredient_name, nutrient_id)
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
    buy_quantity        NUMERIC     NOT NULL,   
    PRIMARY KEY (list_id, ingredient_name)
);
 
CREATE TABLE intake_planner_save_to_list_post (
    planner_id  BIGINT      NOT NULL REFERENCES intake_planner(id) ON DELETE CASCADE,
    post_id     BIGINT      NOT NULL REFERENCES post(id)           ON DELETE CASCADE,
    PRIMARY KEY (planner_id, post_id)
);
 
 