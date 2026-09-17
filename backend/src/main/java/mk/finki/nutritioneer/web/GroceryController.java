package mk.finki.nutritioneer.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mk.finki.nutritioneer.service.GroceryService;
import mk.finki.nutritioneer.web.dto.GroceryDtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grocery-lists")
@RequiredArgsConstructor
@Tag(name = "Grocery lists", description = "UseCase1003")
public class GroceryController {

    private final GroceryService grocery;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a list from whole recipes plus individual items")
    public ListView create(@Valid @RequestBody CreateListRequest request) {
        return grocery.create(request);
    }

    @GetMapping
    @Operation(summary = "The signed-in user's lists")
    public List<ListView> mine() {
        return grocery.mine();
    }

    @GetMapping("/{id}")
    @Operation(summary = "One list with its consolidated shopping lines")
    public ListView get(@PathVariable Long id) {
        return grocery.get(id);
    }

    @PatchMapping("/{id}/bought")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Mark the list as bought")
    public void markBought(@PathVariable Long id, @RequestParam boolean value) {
        grocery.markBought(id, value);
    }
}
