--FIX: Change JOINS {user_email, Created_by, ingredient_name -> user_id, user_id, ingredient_id}

INSERT INTO "user" (email, username, password, role) VALUES
('admin@nutritioneer.mk',           'admin_nutri',    'testPass@1', 'administrator'),
('viktor.ilievski@nutritioneer.mk', 'coach_viktor',   'testPass@1', 'trainer'),
('teodora.mihajlova@nutritioneer.mk','coach_teodora', 'testPass@1', 'trainer'),
('marija.trajkovska@gmail.com',     'marija_t',       'testPass@1', 'user'),
('stefan.petrov@gmail.com',         'stefan_p',       'testPass@1', 'user'),
('ana.jovanovska@yahoo.com',        'ana_j',          'testPass@1', 'user'),
('nikola.stojanov@gmail.com',       'nikola_s',       'testPass@1', 'user'),
('elena.dimitrova@hotmail.com',     'elena_d',        'testPass@1', 'user'),
('bojan.kostov@gmail.com',          'bojan_k',        'testPass@1', 'user'),
('filip.angelov@gmail.com',         'filip_a',        'testPass@1', 'user');

INSERT INTO ingredient (name, description, energy, kcal, type) VALUES
('Chicken breast',      'Skinless boneless chicken breast, raw',            690,  165, 'meat'),
('Beef mince',          'Ground beef, 20 percent fat, raw',                1046,  250, 'meat'),
('Pork shoulder',       'Pork shoulder cut, raw',                          1013,  242, 'meat'),
('Trout fillet',        'Freshwater trout fillet, raw',                     619,  148, 'fish'),
('Mackerel',            'Atlantic mackerel, raw',                           858,  205, 'fish'),
('Egg',                 'Whole chicken egg, raw',                           598,  143, 'other'),
('Cow milk',            'Pasteurised cow milk, 3.2 percent fat',            255,   61, 'dairy'),
('Yogurt',              'Plain yogurt, 3.5 percent fat',                    264,   63, 'dairy'),
('White brine cheese',  'Traditional white brine cheese (sirenje)',        1105,  264, 'dairy'),
('Kashkaval',           'Semi-hard yellow cheese (kashkaval)',             1490,  356, 'dairy'),
('Butter',              'Unsalted cow butter, 82 percent fat',             3000,  717, 'dairy'),
('Sunflower oil',       'Refined sunflower oil',                           3699,  884, 'oil'),
('Olive oil',           'Extra virgin olive oil',                          3699,  884, 'oil'),
('White rice',          'Long grain white rice, uncooked',                 1527,  365, 'carb'),
('Bulgur',              'Coarse bulgur wheat, uncooked',                   1431,  342, 'carb'),
('White flour',         'Wheat flour type 500, all purpose',               1523,  364, 'carb'),
('Spaghetti',           'Durum wheat spaghetti, dry',                      1552,  371, 'carb'),
('White bread',         'White wheat bread, baked',                        1109,  265, 'carb'),
('Oats',                'Rolled oat flakes',                               1628,  389, 'carb'),
('Potato',              'White potato, raw, peeled',                        322,   77, 'vegetable'),
('Dry white beans',     'Dry white beans (tetovec variety)',               1393,  333, 'legume'),
('Lentils',             'Dry brown lentils',                               1473,  352, 'legume'),
('Chickpeas',           'Dry chickpeas',                                   1523,  364, 'legume'),
('Tomato',              'Fresh ripe tomato',                                 75,   18, 'vegetable'),
('Red bell pepper',     'Fresh red bell pepper (kapija)',                   130,   31, 'vegetable'),
('Onion',               'Yellow onion, raw',                                167,   40, 'vegetable'),
('Garlic',              'Fresh garlic cloves',                              623,  149, 'vegetable'),
('Cucumber',            'Fresh cucumber with skin',                          63,   15, 'vegetable'),
('Aubergine',           'Fresh aubergine (eggplant)',                       105,   25, 'vegetable'),
('Zucchini',            'Fresh green zucchini',                              71,   17, 'vegetable'),
('Carrot',              'Fresh carrot, raw',                                172,   41, 'vegetable'),
('Spinach',             'Fresh spinach leaves',                              96,   23, 'vegetable'),
('Cabbage',             'White cabbage, raw',                               105,   25, 'vegetable'),
('Leek',                'Fresh leek, raw',                                  255,   61, 'vegetable'),
('Apple',               'Fresh apple with skin',                            218,   52, 'fruit'),
('Banana',              'Fresh banana, peeled',                             372,   89, 'fruit'),
('Strawberry',          'Fresh strawberries',                               134,   32, 'fruit'),
('Grapes',              'Fresh table grapes',                               288,   69, 'fruit'),
('Lemon',               'Fresh lemon, peeled',                              121,   29, 'fruit'),
('Walnut',              'Shelled walnut kernels',                          2736,  654, 'nut'),
('Almond',              'Raw almonds',                                     2423,  579, 'nut'),
('Sunflower seed',      'Hulled sunflower seeds',                          2444,  584, 'seed'),
('Chia seed',           'Whole chia seeds',                                2033,  486, 'seed'),
('Parsley',             'Fresh flat leaf parsley',                          151,   36, 'herb'),
('Dill',                'Fresh dill weed',                                  180,   43, 'herb'),
('Salt',                'Refined table salt, iodised',                        0,    0, 'spice'),
('Black pepper',        'Ground black pepper',                             1050,  251, 'spice'),
('Paprika powder',      'Sweet red paprika powder',                        1180,  282, 'spice'),
('Honey',               'Raw flower honey',                                1272,  304, 'other'),
('Water',               'Drinking water',                                     0,    0, 'beverage');

INSERT INTO nutrient (description, quantity, unit, type) VALUES
('Protein',             50,   'gram',      'macro'),
('Total carbohydrate', 260,   'gram',      'macro'),
('Total fat',           70,   'gram',      'macro'),
('Saturated fat',       20,   'gram',      'macro'),
('Dietary fibre',       30,   'gram',      'macro'),
('Sugars',              90,   'gram',      'macro'),
('Vitamin C',           80,   'milligram', 'micro'),
('Vitamin A',          800,   'microgram', 'micro'),
('Vitamin D',            5,   'microgram', 'micro'),
('Vitamin E',           12,   'milligram', 'micro'),
('Vitamin K',           75,   'microgram', 'micro'),
('Vitamin B12',        2.5,   'microgram', 'micro'),
('Folate',             200,   'microgram', 'micro'),
('Calcium',            800,   'milligram', 'micro'),
('Iron',                14,   'milligram', 'micro'),
('Magnesium',          375,   'milligram', 'micro'),
('Potassium',         2000,   'milligram', 'micro'),
('Zinc',                10,   'milligram', 'micro'),
('Sodium',            2000,   'milligram', 'micro'),
('Phosphorus',         700,   'milligram', 'micro'),
('Selenium',            55,   'microgram', 'micro'),
('Iodine',             150,   'microgram', 'micro');

INSERT INTO restriction (description, type) VALUES
('Contains no ingredient of animal origin',                 'vegan'),
('Contains no meat and no fish',                            'vegetarian'),
('Prepared according to Islamic dietary law',               'halal'),
('Prepared according to Jewish dietary law',                'kosher'),
('Fish and seafood allowed, no other meat',                 'pescatarian'),
('Contains gluten from wheat, barley or rye',               'allergen'),
('Contains milk protein and lactose',                       'allergen'),
('Contains tree nuts',                                      'allergen'),
('Contains egg',                                            'allergen'),
('Contains fish',                                           'allergen'),
('Low carbohydrate and high fat, ketogenic',                'keto'),
('No grains, legumes or processed food',                    'paleo'),
('Free of gluten containing cereals',                       'gluten_free'),
('Free of lactose',                                         'lactose_free'),
('Suitable for a diabetic meal plan, low glycaemic load',   'diabetic'),
('Reduced sodium content, under 120 mg per 100 g',          'low_sodium');

INSERT INTO ingredient_contains_nutrient (ingredient_id, nutrient_id, quantity)
SELECT i.id, n.id, v.qty
FROM (VALUES
    ('Chicken breast',     31.0,   0.0,   3.6,   0.0),
    ('Beef mince',         26.0,   0.0,  15.0,   0.0),
    ('Pork shoulder',      20.0,   0.0,  18.0,   0.0),
    ('Trout fillet',       20.5,   0.0,   6.6,   0.0),
    ('Mackerel',           19.0,   0.0,  13.9,   0.0),
    ('Egg',                12.6,   0.7,   9.5,   0.0),
    ('Cow milk',            3.3,   4.8,   3.2,   0.0),
    ('Yogurt',              3.5,   4.7,   3.5,   0.0),
    ('White brine cheese', 14.0,   2.0,  21.0,   0.0),
    ('Kashkaval',          25.0,   1.5,  28.0,   0.0),
    ('Butter',              0.9,   0.1,  81.0,   0.0),
    ('Sunflower oil',       0.0,   0.0, 100.0,   0.0),
    ('Olive oil',           0.0,   0.0, 100.0,   0.0),
    ('White rice',          7.0,  80.0,   0.7,   1.3),
    ('Bulgur',             12.0,  76.0,   1.3,  18.0),
    ('White flour',        10.0,  76.0,   1.0,   2.7),
    ('Spaghetti',          13.0,  75.0,   1.5,   3.2),
    ('White bread',         9.0,  49.0,   3.2,   2.7),
    ('Oats',               17.0,  66.0,   7.0,  11.0),
    ('Potato',              2.0,  17.0,   0.1,   2.2),
    ('Dry white beans',    21.0,  60.0,   1.2,  15.0),
    ('Lentils',            25.0,  60.0,   1.1,  30.0),
    ('Chickpeas',          19.0,  61.0,   6.0,  17.0),
    ('Tomato',              0.9,   3.9,   0.2,   1.2),
    ('Red bell pepper',     1.0,   6.0,   0.3,   2.1),
    ('Onion',               1.1,   9.3,   0.1,   1.7),
    ('Garlic',              6.4,  33.0,   0.5,   2.1),
    ('Cucumber',            0.7,   3.6,   0.1,   0.5),
    ('Aubergine',           1.0,   5.9,   0.2,   3.0),
    ('Zucchini',            1.2,   3.1,   0.3,   1.0),
    ('Carrot',              0.9,   9.6,   0.2,   2.8),
    ('Spinach',             2.9,   3.6,   0.4,   2.2),
    ('Cabbage',             1.3,   5.8,   0.1,   2.5),
    ('Leek',                1.5,  14.0,   0.3,   1.8),
    ('Apple',               0.3,  14.0,   0.2,   2.4),
    ('Banana',              1.1,  23.0,   0.3,   2.6),
    ('Strawberry',          0.7,   7.7,   0.3,   2.0),
    ('Grapes',              0.7,  18.0,   0.2,   0.9),
    ('Lemon',               1.1,   9.3,   0.3,   2.8),
    ('Walnut',             15.0,  14.0,  65.0,   6.7),
    ('Almond',             21.0,  22.0,  50.0,  12.5),
    ('Sunflower seed',     21.0,  20.0,  51.0,   8.6),
    ('Chia seed',          17.0,  42.0,  31.0,  34.0),
    ('Parsley',             3.0,   6.3,   0.8,   3.3),
    ('Dill',                3.5,   7.0,   1.1,   2.1),
    ('Paprika powder',     14.0,  54.0,  13.0,  35.0),
    ('Black pepper',       10.0,  64.0,   3.3,  25.0),
    ('Honey',               0.3,  82.0,   0.0,   0.2)
) AS m(ing, protein, carbohydrate, fat, fibre)
CROSS JOIN LATERAL (VALUES
    ('Protein',            m.protein),
    ('Total carbohydrate', m.carbohydrate),
    ('Total fat',          m.fat),
    ('Dietary fibre',      m.fibre)
) AS v(nut, qty)
JOIN nutrient   n ON n.description = v.nut
JOIN ingredient i ON i.name        = m.ing;

INSERT INTO ingredient_contains_nutrient (ingredient_id, nutrient_id, quantity)
SELECT i.id, n.id, v.qty
FROM (VALUES
    ('Red bell pepper', 'Vitamin C',    128.0),
    ('Parsley',         'Vitamin C',    133.0),
    ('Spinach',         'Vitamin C',     28.0),
    ('Tomato',          'Vitamin C',     14.0),
    ('Cabbage',         'Vitamin C',     36.0),
    ('Strawberry',      'Vitamin C',     59.0),
    ('Potato',          'Vitamin C',     20.0),
    ('Lemon',           'Vitamin C',     53.0),
    ('Carrot',          'Vitamin A',    835.0),
    ('Spinach',         'Vitamin A',    469.0),
    ('Red bell pepper', 'Vitamin A',    157.0),
    ('Butter',          'Vitamin A',    684.0),
    ('Trout fillet',    'Vitamin D',      6.5),
    ('Mackerel',        'Vitamin D',     16.0),
    ('Egg',             'Vitamin D',      2.0),
    ('Beef mince',      'Vitamin B12',    2.6),
    ('Trout fillet',    'Vitamin B12',    4.5),
    ('Mackerel',        'Vitamin B12',    8.7),
    ('Egg',             'Vitamin B12',    1.1),
    ('Cow milk',        'Vitamin B12',    0.5),
    ('Spinach',         'Folate',       194.0),
    ('Lentils',         'Folate',       479.0),
    ('Dry white beans', 'Folate',       388.0),
    ('Parsley',         'Folate',       152.0),
    ('Cow milk',        'Calcium',      120.0),
    ('Yogurt',          'Calcium',      121.0),
    ('White brine cheese','Calcium',    493.0),
    ('Kashkaval',       'Calcium',      700.0),
    ('Spinach',         'Calcium',       99.0),
    ('Almond',          'Calcium',      269.0),
    ('Chia seed',       'Calcium',      631.0),
    ('Spinach',         'Iron',           2.7),
    ('Lentils',         'Iron',           7.5),
    ('Dry white beans', 'Iron',           5.5),
    ('Beef mince',      'Iron',           2.6),
    ('Oats',            'Iron',           4.7),
    ('Chia seed',       'Iron',           7.7),
    ('Almond',          'Magnesium',    270.0),
    ('Oats',            'Magnesium',    177.0),
    ('Spinach',         'Magnesium',     79.0),
    ('Walnut',          'Magnesium',    158.0),
    ('Potato',          'Potassium',    425.0),
    ('Banana',          'Potassium',    358.0),
    ('Spinach',         'Potassium',    558.0),
    ('Tomato',          'Potassium',    237.0),
    ('Dry white beans', 'Potassium',   1795.0),
    ('Beef mince',      'Zinc',           4.8),
    ('Lentils',         'Zinc',           3.3),
    ('Oats',            'Zinc',           4.0),
    ('Salt',            'Sodium',     38758.0),
    ('White brine cheese','Sodium',    1200.0),
    ('White bread',     'Sodium',       490.0),
    ('Kashkaval',       'Sodium',       800.0),
    ('Salt',            'Iodine',      2500.0),
    ('Trout fillet',    'Selenium',      12.6),
    ('Mackerel',        'Selenium',      44.1),
    ('Chicken breast',  'Selenium',      22.8),
    ('Chicken breast',  'Phosphorus',   210.0),
    ('Cow milk',        'Phosphorus',    92.0),
    ('Almond',          'Vitamin E',     25.6),
    ('Sunflower oil',   'Vitamin E',     41.1),
    ('Olive oil',       'Vitamin E',     14.4),
    ('Spinach',         'Vitamin K',    483.0),
    ('Parsley',         'Vitamin K',   1640.0)
) AS v(ing, nut, qty)
JOIN nutrient   n ON n.description = v.nut
JOIN ingredient i ON i.name        = v.ing;

INSERT INTO recipe (name, guide, kcal_sum, servings, user_id)
SELECT v.name, v.guide, v.kcal_sum, v.servings, u.id
FROM (VALUES
('Tavce Gravce',
 E'1. Soak the beans overnight, then boil until soft.\n2. Fry the chopped onion in oil, add paprika off the heat.\n3. Combine everything in a clay pan, season and bake at 200 C for 40 minutes.\n4. Serve hot with fresh bread.',
 0, 6, 'marija.trajkovska@gmail.com'),

('Shopska Salad',
 E'1. Dice the tomatoes and cucumbers, slice the onion thinly.\n2. Season with salt and olive oil and toss.\n3. Grate the white brine cheese generously over the top and serve immediately.',
 0, 4, 'stefan.petrov@gmail.com'),

('Turli Tava',
 E'1. Brown the beef mince with the onion.\n2. Cut all vegetables into large chunks and layer them in a deep tray.\n3. Add the meat, oil, spices and a cup of water.\n4. Bake covered at 190 C for 60 minutes, then 15 minutes uncovered.',
 0, 6, 'ana.jovanovska@yahoo.com'),

('Homemade Ajvar',
 E'1. Roast the peppers and aubergine until the skins blister, then peel them.\n2. Mince the flesh and cook it slowly in oil, stirring constantly for about two hours.\n3. Add garlic and salt near the end and jar while hot.',
 0, 20, 'elena.dimitrova@hotmail.com'),

('Grilled Trout with Parsley',
 E'1. Clean the fillets and pat them dry.\n2. Rub with olive oil, crushed garlic, salt and pepper.\n3. Grill 4 minutes per side and finish with chopped parsley and lemon.',
 0, 2, 'nikola.stojanov@gmail.com'),

('Chicken Bulgur Bowl',
 E'1. Boil the bulgur in twice its volume of water for 12 minutes.\n2. Grill the seasoned chicken breast and slice it.\n3. Assemble with diced tomato and cucumber, finish with olive oil.',
 0, 3, 'viktor.ilievski@nutritioneer.mk'),

('Oat Porridge with Banana and Walnuts',
 E'1. Simmer the oats in milk for 5 minutes, stirring.\n2. Pour into a bowl and top with sliced banana and crushed walnuts.\n3. Drizzle honey over the top.',
 0, 2, 'marija.trajkovska@gmail.com'),

('Lentil Soup',
 E'1. Saute the diced onion, carrot and garlic in olive oil.\n2. Add the rinsed lentils and 1.5 litres of water.\n3. Simmer for 35 minutes, season and blend half of the soup for a thicker texture.',
 0, 5, 'bojan.kostov@gmail.com'),

('Zelnik Spinach and Cheese Filling',
 E'1. Wilt the washed spinach and squeeze out all excess water.\n2. Crumble in the white brine cheese and mix with beaten egg and oil.\n3. Season lightly - the cheese is already salty. Use as filling for zelnik.',
 0, 8, 'elena.dimitrova@hotmail.com'),

('Tarator',
 E'1. Grate the cucumber and drain it briefly.\n2. Whisk the yogurt with crushed garlic, chopped dill, salt and olive oil.\n3. Fold in the cucumber and crushed walnuts, chill for one hour before serving.',
 0, 4, 'ana.jovanovska@yahoo.com'),

('Baked Mackerel with Vegetables',
 E'1. Slice the potatoes, carrots and onion and spread them on a tray with oil and salt.\n2. Bake at 200 C for 25 minutes.\n3. Place the cleaned mackerel on top and bake for another 20 minutes.',
 0, 3, 'nikola.stojanov@gmail.com'),

('Protein Pancakes',
 E'1. Blend the oats, eggs, milk and banana into a smooth batter.\n2. Rest the batter for 10 minutes.\n3. Fry small pancakes in a little butter over medium heat, about 2 minutes per side.',
 0, 3, 'teodora.mihajlova@nutritioneer.mk'),

('Grilled Chicken Salad',
 E'1. Grill the seasoned chicken breast and let it rest, then slice.\n2. Toss the spinach, tomato and cucumber with olive oil and salt.\n3. Arrange the chicken on top and serve at room temperature.',
 0, 2, 'stefan.petrov@gmail.com'),

('Stuffed Peppers with Rice',
 E'1. Core the peppers carefully without splitting them.\n2. Mix the rice, browned mince, onion, oil and spices.\n3. Fill the peppers, stand them upright in a pot with water and simmer for 50 minutes.',
 0, 4, 'filip.angelov@gmail.com')
) AS v(name, guide, kcal_sum, servings, email)
JOIN "user" u ON u.email = v.email;

INSERT INTO recipe_contains_ingredient (recipe_id, ingredient_id, ingredient_quantity)
SELECT r.id, i.id, v.qty
FROM (VALUES
    ('Tavce Gravce','Dry white beans',500), ('Tavce Gravce','Onion',200),
    ('Tavce Gravce','Red bell pepper',100), ('Tavce Gravce','Sunflower oil',60),
    ('Tavce Gravce','Paprika powder',10),   ('Tavce Gravce','Salt',8),
    ('Tavce Gravce','Black pepper',2),      ('Tavce Gravce','Parsley',15),
    ('Shopska Salad','Tomato',400),         ('Shopska Salad','Cucumber',300),
    ('Shopska Salad','Onion',80),           ('Shopska Salad','White brine cheese',150),
    ('Shopska Salad','Olive oil',30),       ('Shopska Salad','Salt',5),
    ('Turli Tava','Beef mince',300),        ('Turli Tava','Potato',400),
    ('Turli Tava','Aubergine',300),         ('Turli Tava','Zucchini',250),
    ('Turli Tava','Red bell pepper',200),   ('Turli Tava','Tomato',250),
    ('Turli Tava','Onion',150),             ('Turli Tava','Sunflower oil',50),
    ('Turli Tava','Salt',10),               ('Turli Tava','Black pepper',3),
    ('Turli Tava','Paprika powder',8),
    ('Homemade Ajvar','Red bell pepper',2000), ('Homemade Ajvar','Aubergine',500),
    ('Homemade Ajvar','Garlic',30),            ('Homemade Ajvar','Sunflower oil',200),
    ('Homemade Ajvar','Salt',20),
    ('Grilled Trout with Parsley','Trout fillet',400), ('Grilled Trout with Parsley','Olive oil',20),
    ('Grilled Trout with Parsley','Garlic',10),        ('Grilled Trout with Parsley','Parsley',20),
    ('Grilled Trout with Parsley','Lemon',60),         ('Grilled Trout with Parsley','Salt',5),
    ('Grilled Trout with Parsley','Black pepper',2),
    ('Chicken Bulgur Bowl','Chicken breast',400), ('Chicken Bulgur Bowl','Bulgur',200),
    ('Chicken Bulgur Bowl','Tomato',150),         ('Chicken Bulgur Bowl','Cucumber',100),
    ('Chicken Bulgur Bowl','Olive oil',20),       ('Chicken Bulgur Bowl','Salt',5),
    ('Oat Porridge with Banana and Walnuts','Oats',80),   ('Oat Porridge with Banana and Walnuts','Cow milk',250),
    ('Oat Porridge with Banana and Walnuts','Banana',120),('Oat Porridge with Banana and Walnuts','Walnut',30),
    ('Oat Porridge with Banana and Walnuts','Honey',20),
    ('Lentil Soup','Lentils',300),   ('Lentil Soup','Carrot',150),
    ('Lentil Soup','Onion',120),     ('Lentil Soup','Garlic',15),
    ('Lentil Soup','Olive oil',30),  ('Lentil Soup','Salt',8),
    ('Lentil Soup','Black pepper',2),
    ('Zelnik Spinach and Cheese Filling','Spinach',500),  ('Zelnik Spinach and Cheese Filling','White brine cheese',300),
    ('Zelnik Spinach and Cheese Filling','Egg',120),      ('Zelnik Spinach and Cheese Filling','Sunflower oil',40),
    ('Zelnik Spinach and Cheese Filling','Salt',6),
    ('Tarator','Yogurt',500),   ('Tarator','Cucumber',300),
    ('Tarator','Garlic',10),    ('Tarator','Dill',15),
    ('Tarator','Olive oil',15), ('Tarator','Walnut',25),
    ('Tarator','Salt',4),
    ('Baked Mackerel with Vegetables','Mackerel',500), ('Baked Mackerel with Vegetables','Potato',300),
    ('Baked Mackerel with Vegetables','Carrot',150),   ('Baked Mackerel with Vegetables','Onion',100),
    ('Baked Mackerel with Vegetables','Olive oil',25), ('Baked Mackerel with Vegetables','Salt',6),
    ('Baked Mackerel with Vegetables','Black pepper',2),
    ('Protein Pancakes','Oats',100),    ('Protein Pancakes','Egg',150),
    ('Protein Pancakes','Cow milk',150),('Protein Pancakes','Banana',100),
    ('Protein Pancakes','Butter',15),
    ('Grilled Chicken Salad','Chicken breast',300), ('Grilled Chicken Salad','Spinach',150),
    ('Grilled Chicken Salad','Tomato',150),         ('Grilled Chicken Salad','Cucumber',100),
    ('Grilled Chicken Salad','Olive oil',20),       ('Grilled Chicken Salad','Salt',4),
    ('Stuffed Peppers with Rice','Red bell pepper',600), ('Stuffed Peppers with Rice','White rice',200),
    ('Stuffed Peppers with Rice','Beef mince',250),      ('Stuffed Peppers with Rice','Onion',100),
    ('Stuffed Peppers with Rice','Sunflower oil',40),    ('Stuffed Peppers with Rice','Salt',8),
    ('Stuffed Peppers with Rice','Paprika powder',5)
) AS v(recipe_name, ing, qty)
JOIN recipe     r ON r.name = v.recipe_name
JOIN ingredient i ON i.name = v.ing;

INSERT INTO recipe_contains_restriction (recipe_id, restriction_id)
SELECT r.id, res.id
FROM (VALUES
    ('Tavce Gravce','Contains no ingredient of animal origin'),
    ('Tavce Gravce','Contains no meat and no fish'),
    ('Tavce Gravce','Free of gluten containing cereals'),
    ('Tavce Gravce','Free of lactose'),
    ('Shopska Salad','Contains no meat and no fish'),
    ('Shopska Salad','Contains milk protein and lactose'),
    ('Shopska Salad','Free of gluten containing cereals'),
    ('Turli Tava','Free of gluten containing cereals'),
    ('Turli Tava','Free of lactose'),
    ('Homemade Ajvar','Contains no ingredient of animal origin'),
    ('Homemade Ajvar','Contains no meat and no fish'),
    ('Homemade Ajvar','Free of gluten containing cereals'),
    ('Homemade Ajvar','Free of lactose'),
    ('Grilled Trout with Parsley','Fish and seafood allowed, no other meat'),
    ('Grilled Trout with Parsley','Contains fish'),
    ('Grilled Trout with Parsley','Free of gluten containing cereals'),
    ('Grilled Trout with Parsley','Low carbohydrate and high fat, ketogenic'),
    ('Chicken Bulgur Bowl','Contains gluten from wheat, barley or rye'),
    ('Chicken Bulgur Bowl','Free of lactose'),
    ('Oat Porridge with Banana and Walnuts','Contains no meat and no fish'),
    ('Oat Porridge with Banana and Walnuts','Contains milk protein and lactose'),
    ('Oat Porridge with Banana and Walnuts','Contains tree nuts'),
    ('Lentil Soup','Contains no ingredient of animal origin'),
    ('Lentil Soup','Contains no meat and no fish'),
    ('Lentil Soup','Free of gluten containing cereals'),
    ('Lentil Soup','Free of lactose'),
    ('Zelnik Spinach and Cheese Filling','Contains no meat and no fish'),
    ('Zelnik Spinach and Cheese Filling','Contains milk protein and lactose'),
    ('Zelnik Spinach and Cheese Filling','Contains egg'),
    ('Tarator','Contains no meat and no fish'),
    ('Tarator','Contains milk protein and lactose'),
    ('Tarator','Contains tree nuts'),
    ('Tarator','Free of gluten containing cereals'),
    ('Baked Mackerel with Vegetables','Fish and seafood allowed, no other meat'),
    ('Baked Mackerel with Vegetables','Contains fish'),
    ('Baked Mackerel with Vegetables','Free of lactose'),
    ('Protein Pancakes','Contains no meat and no fish'),
    ('Protein Pancakes','Contains milk protein and lactose'),
    ('Protein Pancakes','Contains egg'),
    ('Grilled Chicken Salad','Free of gluten containing cereals'),
    ('Grilled Chicken Salad','Free of lactose'),
    ('Grilled Chicken Salad','Suitable for a diabetic meal plan, low glycaemic load'),
    ('Stuffed Peppers with Rice','Free of gluten containing cereals'),
    ('Stuffed Peppers with Rice','Free of lactose')
) AS v(recipe_name, restriction_description)
JOIN recipe      r   ON r.name        = v.recipe_name
JOIN restriction res ON res.description = v.restriction_description;

INSERT INTO post (user_id, recipe_id, is_private, is_favourite, status, created_at, media, media_type, media_name)
VALUES ((SELECT id FROM "user" WHERE email = 'marija.trajkovska@gmail.com'), NULL, TRUE, FALSE, 'published', '2026-01-30 07:30:00',
        decode('iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8z8BQDwAEhQGAhKmMIQAAAABJRU5ErkJggg==', 'base64'),
        'image/png', 'progress_week4.png');

INSERT INTO post (user_id, recipe_id, is_private, is_favourite, status, created_at, media, media_type, media_name)
SELECT u.id, r.id, v.is_private, v.is_favourite, v.status::post_status, v.created_at::timestamp, NULL, NULL, v.media_name
FROM (VALUES
    ('marija.trajkovska@gmail.com',      'Tavce Gravce',                          FALSE, TRUE,  'published', '2026-01-12 18:20:00', 'tavce_gravce.jpg'),
    ('stefan.petrov@gmail.com',          'Shopska Salad',                         FALSE, FALSE, 'published', '2026-01-14 12:05:00', 'shopska.jpg'),
    ('ana.jovanovska@yahoo.com',         'Turli Tava',                            FALSE, TRUE,  'published', '2026-01-15 19:40:00', 'turli_tava.jpg'),
    ('elena.dimitrova@hotmail.com',      'Homemade Ajvar',                        FALSE, TRUE,  'published', '2026-01-18 16:10:00', 'ajvar_jars.jpg'),
    ('nikola.stojanov@gmail.com',        'Grilled Trout with Parsley',            FALSE, FALSE, 'published', '2026-01-20 20:00:00', 'trout.jpg'),
    ('viktor.ilievski@nutritioneer.mk',  'Chicken Bulgur Bowl',                   FALSE, TRUE,  'published', '2026-01-21 13:30:00', 'bulgur_bowl.jpg'),
    ('marija.trajkovska@gmail.com',      'Oat Porridge with Banana and Walnuts',  TRUE,  FALSE, 'published', '2026-01-22 08:15:00', 'porridge.jpg'),
    ('bojan.kostov@gmail.com',           'Lentil Soup',                           FALSE, FALSE, 'draft',     '2026-01-23 17:45:00', NULL),
    ('elena.dimitrova@hotmail.com',      'Zelnik Spinach and Cheese Filling',     FALSE, FALSE, 'archived',  '2026-01-05 11:00:00', 'zelnik.jpg'),
    ('ana.jovanovska@yahoo.com',         'Tarator',                               FALSE, TRUE,  'published', '2026-01-25 14:20:00', 'tarator.jpg'),
    ('nikola.stojanov@gmail.com',        'Baked Mackerel with Vegetables',        FALSE, FALSE, 'published', '2026-01-26 19:10:00', 'mackerel.jpg'),
    ('teodora.mihajlova@nutritioneer.mk','Protein Pancakes',                      FALSE, TRUE,  'published', '2026-01-27 09:00:00', 'pancakes.jpg'),
    ('stefan.petrov@gmail.com',          'Grilled Chicken Salad',                 TRUE,  FALSE, 'draft',     '2026-01-28 12:40:00', NULL),
    ('filip.angelov@gmail.com',          'Stuffed Peppers with Rice',             FALSE, FALSE, 'published', '2026-01-29 18:55:00', 'peppers.jpg')
) AS v(author, recipe_name, is_private, is_favourite, status, created_at, media_name)
JOIN recipe r ON r.name  = v.recipe_name
JOIN "user" u ON u.email = v.author;
 
INSERT INTO comment (description, created_at, post_id, user_id)
SELECT v.body, v.created_at::timestamp, p.id, u.id
FROM (VALUES
    ('Tavce Gravce',               'stefan.petrov@gmail.com',          'Exactly how my grandmother made it. Adding it to my planner.', '2026-01-12 19:02:00'),
    ('Tavce Gravce',               'ana.jovanovska@yahoo.com',         'How long do the beans need to soak?',                          '2026-01-13 09:11:00'),
    ('Tavce Gravce',               'marija.trajkovska@gmail.com',      'At least eight hours, overnight is best.',                     '2026-01-13 09:40:00'),
    ('Shopska Salad',              'elena.dimitrova@hotmail.com',      'Simple and perfect. I use half the cheese to cut the sodium.',  '2026-01-14 13:20:00'),
    ('Turli Tava',                 'bojan.kostov@gmail.com',           'Made it without the mince, still excellent.',                  '2026-01-16 20:15:00'),
    ('Turli Tava',                 'viktor.ilievski@nutritioneer.mk',  'Good macro balance for a rest day.',                           '2026-01-16 21:00:00'),
    ('Homemade Ajvar',             'marija.trajkovska@gmail.com',      'Two hours of stirring is no joke but worth every minute.',      '2026-01-19 10:05:00'),
    ('Homemade Ajvar',             'filip.angelov@gmail.com',          'How many jars does this quantity fill?',                       '2026-01-19 11:30:00'),
    ('Grilled Trout with Parsley', 'teodora.mihajlova@nutritioneer.mk','Very clean protein source, great for a cutting phase.',         '2026-01-21 08:45:00'),
    ('Chicken Bulgur Bowl',        'stefan.petrov@gmail.com',          'This is now my default meal prep for the week.',                '2026-01-21 18:10:00'),
    ('Chicken Bulgur Bowl',        'nikola.stojanov@gmail.com',        'Swapped bulgur for rice to keep it gluten free.',               '2026-01-22 12:25:00'),
    ('Tarator',                    'stefan.petrov@gmail.com',          'Draining the cucumber really does make a difference.',          '2026-01-25 16:00:00'),
    ('Baked Mackerel with Vegetables','ana.jovanovska@yahoo.com',      'The vitamin D content on this one is impressive.',              '2026-01-27 08:20:00'),
    ('Protein Pancakes',           'marija.trajkovska@gmail.com',      'Thirty grams of protein in a breakfast, finally.',              '2026-01-27 10:15:00'),
    ('Stuffed Peppers with Rice',  'elena.dimitrova@hotmail.com',      'Try it with a spoon of ajvar mixed into the filling.',          '2026-01-30 09:05:00')
) AS v(recipe_name, author, body, created_at)
JOIN recipe r ON r.name      = v.recipe_name
JOIN post   p ON p.recipe_id = r.id
JOIN "user" u ON u.email     = v.author;


INSERT INTO grocery_list (date_time, notes, is_bought, kcal, user_id)
SELECT v.dt::timestamp, v.notes, v.is_bought, v.kcal, u.id
FROM (VALUES
('2026-01-11 10:00:00', 'Weekly shopping - Sunday cooking',        TRUE,  0, 'marija.trajkovska@gmail.com'),
('2026-01-14 17:30:00', 'Vegetables for the tray bake',            TRUE,  0, 'ana.jovanovska@yahoo.com'),
('2026-01-17 09:15:00', 'Ajvar season - green market',             TRUE,  0, 'elena.dimitrova@hotmail.com'),
('2026-01-19 18:00:00', 'Fish market on Saturday morning',         FALSE, 0, 'nikola.stojanov@gmail.com'),
('2026-01-22 11:45:00', 'Cheap protein week',                      FALSE, 0, 'bojan.kostov@gmail.com')
) AS v(dt, notes, is_bought, kcal, email)
JOIN "user" u ON u.email = v.email;

-- Bulk add
INSERT INTO grocery_list_bulk_add_ingredient (list_id, recipe_id)
SELECT g.id, r.id
FROM (VALUES
    ('marija.trajkovska@gmail.com', '2026-01-11 10:00:00', 'Tavce Gravce'),
    ('marija.trajkovska@gmail.com', '2026-01-11 10:00:00', 'Oat Porridge with Banana and Walnuts'),
    ('ana.jovanovska@yahoo.com',    '2026-01-14 17:30:00', 'Turli Tava'),
    ('ana.jovanovska@yahoo.com',    '2026-01-14 17:30:00', 'Tarator'),
    ('elena.dimitrova@hotmail.com', '2026-01-17 09:15:00', 'Homemade Ajvar'),
    ('nikola.stojanov@gmail.com',   '2026-01-19 18:00:00', 'Grilled Trout with Parsley'),
    ('nikola.stojanov@gmail.com',   '2026-01-19 18:00:00', 'Baked Mackerel with Vegetables'),
    ('bojan.kostov@gmail.com',      '2026-01-22 11:45:00', 'Lentil Soup')
) AS v(user_email, dt, recipe_name)
JOIN "user"       u ON u.email   = v.user_email
JOIN grocery_list g ON g.user_id = u.id AND g.date_time = v.dt::timestamp
JOIN recipe       r ON r.name    = v.recipe_name;

-- Single add
INSERT INTO grocery_list_single_add_ingredient (list_id, ingredient_id, buy_quantity)
SELECT g.id, i.id, v.qty
FROM (VALUES
    ('marija.trajkovska@gmail.com', '2026-01-11 10:00:00', 'Sunflower oil',   1000),
    ('marija.trajkovska@gmail.com', '2026-01-11 10:00:00', 'Salt',             500),
    ('marija.trajkovska@gmail.com', '2026-01-11 10:00:00', 'Apple',           1500),
    ('ana.jovanovska@yahoo.com',    '2026-01-14 17:30:00', 'Potato',          2000),
    ('ana.jovanovska@yahoo.com',    '2026-01-14 17:30:00', 'White bread',      500),
    ('elena.dimitrova@hotmail.com', '2026-01-17 09:15:00', 'Red bell pepper', 5000),
    ('elena.dimitrova@hotmail.com', '2026-01-17 09:15:00', 'Garlic',           150),
    ('nikola.stojanov@gmail.com',   '2026-01-19 18:00:00', 'Olive oil',        750),
    ('nikola.stojanov@gmail.com',   '2026-01-19 18:00:00', 'Lemon',            300),
    ('bojan.kostov@gmail.com',      '2026-01-22 11:45:00', 'Chickpeas',       1000),
    ('bojan.kostov@gmail.com',      '2026-01-22 11:45:00', 'Carrot',           800),
    ('bojan.kostov@gmail.com',      '2026-01-22 11:45:00', 'Onion',            700)
) AS v(user_email, dt, ing, qty)
JOIN "user"       u ON u.email   = v.user_email
JOIN grocery_list g ON g.user_id = u.id AND g.date_time = v.dt::timestamp
JOIN ingredient   i ON i.name    = v.ing;

INSERT INTO intake_planner (date_time, notes, kcal, is_consumed, user_id)
SELECT v.dt::timestamp, v.notes, v.kcal, v.is_consumed, u.id
FROM (VALUES
('2026-01-22 08:30:00', 'Breakfast',                         0, TRUE,  'marija.trajkovska@gmail.com'),
('2026-01-22 13:00:00', 'Lunch at home',                     0, TRUE,  'marija.trajkovska@gmail.com'),
('2026-01-22 19:30:00', 'Light dinner',                      0, TRUE,  'marija.trajkovska@gmail.com'),
('2026-01-23 08:30:00', 'Breakfast',                         0, TRUE,  'marija.trajkovska@gmail.com'),
('2026-01-23 13:15:00', 'Lunch',                             0, TRUE,  'marija.trajkovska@gmail.com'),
('2026-01-24 09:00:00', 'Late breakfast after training',     0, TRUE,  'marija.trajkovska@gmail.com'),
('2026-01-24 20:00:00', 'Dinner with family',                0, TRUE,  'marija.trajkovska@gmail.com'),
('2026-01-25 12:30:00', 'Planned lunch - not eaten yet',     0, FALSE, 'marija.trajkovska@gmail.com'),
('2026-01-22 12:45:00', 'Meal prep box 1',                   0, TRUE,  'stefan.petrov@gmail.com'),
('2026-01-23 12:45:00', 'Meal prep box 2',                   0, TRUE,  'stefan.petrov@gmail.com'),
('2026-01-26 19:45:00', 'Dinner',                            0, TRUE,  'nikola.stojanov@gmail.com'),
('2026-01-27 09:30:00', 'Post workout breakfast',            0, TRUE,  'teodora.mihajlova@nutritioneer.mk'),
('2026-01-28 13:00:00', 'Lunch',                             0, TRUE,  'ana.jovanovska@yahoo.com'),
('2026-01-29 10:00:00', 'Coffee and fruit only',           180, TRUE,  'bojan.kostov@gmail.com')
) AS v(dt, notes, kcal, is_consumed, email)
JOIN "user" u ON u.email = v.email;

INSERT INTO intake_planner_save_to_list_post (planner_id, post_id)
SELECT ip.id, p.id
FROM (VALUES
    ('marija.trajkovska@gmail.com',     '2026-01-22 08:30:00', 'Oat Porridge with Banana and Walnuts'),
    ('marija.trajkovska@gmail.com',     '2026-01-22 13:00:00', 'Tavce Gravce'),
    ('marija.trajkovska@gmail.com',     '2026-01-22 19:30:00', 'Shopska Salad'),
    ('marija.trajkovska@gmail.com',     '2026-01-23 08:30:00', 'Protein Pancakes'),
    ('marija.trajkovska@gmail.com',     '2026-01-23 13:15:00', 'Chicken Bulgur Bowl'),
    ('marija.trajkovska@gmail.com',     '2026-01-24 09:00:00', 'Oat Porridge with Banana and Walnuts'),
    ('marija.trajkovska@gmail.com',     '2026-01-24 20:00:00', 'Turli Tava'),
    ('marija.trajkovska@gmail.com',     '2026-01-25 12:30:00', 'Grilled Trout with Parsley'),
    ('stefan.petrov@gmail.com',         '2026-01-22 12:45:00', 'Chicken Bulgur Bowl'),
    ('stefan.petrov@gmail.com',         '2026-01-23 12:45:00', 'Chicken Bulgur Bowl'),
    ('nikola.stojanov@gmail.com',       '2026-01-26 19:45:00', 'Baked Mackerel with Vegetables'),
    ('teodora.mihajlova@nutritioneer.mk','2026-01-27 09:30:00','Protein Pancakes'),
    ('ana.jovanovska@yahoo.com',        '2026-01-28 13:00:00', 'Tarator'),
    ('ana.jovanovska@yahoo.com',        '2026-01-28 13:00:00', 'Stuffed Peppers with Rice')
) AS v(user_email, dt, recipe_name)
JOIN "user"          u ON u.email      = v.user_email
JOIN intake_planner ip ON ip.user_id   = u.id AND ip.date_time = v.dt::timestamp
JOIN recipe          r ON r.name        = v.recipe_name
JOIN post            p ON p.recipe_id   = r.id;

INSERT INTO biometrics (date, weight, height, age, muscle_fat_ratio, user_id)
SELECT v.d::date, v.weight, v.height, v.age, v.ratio, u.id
FROM (VALUES
('2026-01-05', 74.5, 168, 29, 1.80, 'marija.trajkovska@gmail.com'),
('2026-01-12', 73.8, 168, 29, 1.84, 'marija.trajkovska@gmail.com'),
('2026-01-19', 73.1, 168, 29, 1.90, 'marija.trajkovska@gmail.com'),
('2026-01-26', 72.4, 168, 29, 1.95, 'marija.trajkovska@gmail.com'),
('2026-01-06',  88.2, 182, 34, 2.10, 'stefan.petrov@gmail.com'),
('2026-01-20',  87.0, 182, 34, 2.18, 'stefan.petrov@gmail.com'),
('2026-01-27',  86.3, 182, 34, 2.24, 'stefan.petrov@gmail.com'),
('2026-01-10',  61.0, 163, 41, 1.65, 'ana.jovanovska@yahoo.com'),
('2026-01-24',  60.4, 163, 41, 1.70, 'ana.jovanovska@yahoo.com'),
('2026-01-08',  95.7, 190, 27, 2.35, 'nikola.stojanov@gmail.com'),
('2026-01-22',  94.9, 190, 27, 2.40, 'nikola.stojanov@gmail.com'),
('2026-01-15',  68.3, 171, 36, 1.72, 'elena.dimitrova@hotmail.com'),
('2026-01-29',  67.9, 171, 36, 1.75, 'elena.dimitrova@hotmail.com'),
('2026-01-18',  79.0, 176, 31, 1.60, 'bojan.kostov@gmail.com')
) AS v(d, weight, height, age, ratio, email)
JOIN "user" u ON u.email = v.email;