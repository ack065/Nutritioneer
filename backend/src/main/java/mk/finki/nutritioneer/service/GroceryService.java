package mk.finki.nutritioneer.service;

import lombok.RequiredArgsConstructor;
import mk.finki.nutritioneer.domain.GroceryItem;
import mk.finki.nutritioneer.domain.GroceryList;
import mk.finki.nutritioneer.domain.Ingredient;
import mk.finki.nutritioneer.domain.Recipe;
import mk.finki.nutritioneer.exception.ApiException;
import mk.finki.nutritioneer.repo.GroceryListRepository;
import mk.finki.nutritioneer.repo.IngredientRepository;
import mk.finki.nutritioneer.repo.RecipeRepository;
import mk.finki.nutritioneer.web.dto.GroceryDtos.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GroceryService {

    private final GroceryListRepository lists;
    private final RecipeRepository recipes;
    private final IngredientRepository ingredients;
    private final CurrentUser currentUser;

    @Transactional
    public ListView create(CreateListRequest request) {
        GroceryList list = GroceryList.builder()
                .owner(currentUser.get())
                .dateTime(request.dateTime() == null ? LocalDateTime.now() : request.dateTime())
                .notes(request.notes())
                .isBought(false)
                .kcal(BigDecimal.ZERO)
                .build();

        if (request.bulkRecipeIds() != null && !request.bulkRecipeIds().isEmpty()) {
            Set<Recipe> bulk = new LinkedHashSet<>(recipes.findAllById(request.bulkRecipeIds()));
            list.setBulkRecipes(bulk);
        }

        if (request.singleItems() != null) {
            for (SingleItem item : request.singleItems()) {
                Ingredient ingredient = ingredients.findById(item.ingredientName())
                        .orElseThrow(() -> ApiException.badRequest(
                                "Unknown ingredient: " + item.ingredientName()));
                list.getSingleItems().add(GroceryItem.builder()
                        .list(list)
                        .ingredient(ingredient)
                        .buyQuantity(item.buyQuantity())
                        .build());
            }
        }

        GroceryList saved = lists.save(list);
        lists.recalculateKcal(saved.getId());

        return view(lists.findById(saved.getId()).orElseThrow());
    }

    @Transactional(readOnly = true)
    public List<ListView> mine() {
        return lists.findByOwnerEmailOrderByDateTimeDesc(currentUser.email())
                .stream().map(this::view).toList();
    }

    @Transactional(readOnly = true)
    public ListView get(Long id) {
        return view(ownedList(id));
    }

    @Transactional
    public void markBought(Long id, boolean bought) {
        ownedList(id).setBought(bought);
    }

    private GroceryList ownedList(Long id) {
        GroceryList list = lists.findById(id)
                .orElseThrow(() -> ApiException.notFound("Grocery list"));
        if (!list.getOwner().getEmail().equals(currentUser.email())) {
            throw ApiException.forbidden("You can only open your own lists");
        }
        return list;
    }

    private ListView view(GroceryList l) {
        List<ShoppingLine> lines = lists.consolidate(l.getId()).stream()
                .map(r -> new ShoppingLine(r.getType(), r.getName(), r.getTotalGrams()))
                .toList();

        List<String> bulk = l.getBulkRecipes().stream().map(Recipe::getName).toList();

        return new ListView(l.getId(), l.getDateTime(), l.getNotes(), l.isBought(),
                l.getKcal(), bulk, lines);
    }
}
