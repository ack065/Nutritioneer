package mk.finki.nutritioneer.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


@Entity
@Table(name = "recipe_contains_ingredient")
@IdClass(RecipeIngredient.Key.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RecipeIngredient {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ingredient_name", nullable = false)
    private Ingredient ingredient;

    @Column(name = "ingredient_quantity", nullable = false)
    private BigDecimal quantity;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class Key implements Serializable {
        private Long recipe;
        private String ingredient;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key key)) return false;
            return Objects.equals(recipe, key.recipe)
                    && Objects.equals(ingredient, key.ingredient);
        }

        @Override
        public int hashCode() {
            return Objects.hash(recipe, ingredient);
        }
    }
}
