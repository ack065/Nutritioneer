package mk.finki.nutritioneer.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ingredient_contains_nutrient")
@IdClass(IngredientNutrient.Key.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class IngredientNutrient {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ingredient_name", nullable = false)
    private Ingredient ingredient;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "nutrient_id", nullable = false)
    private Nutrient nutrient;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class Key implements Serializable {
        private String ingredient;
        private Long nutrient;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key key)) return false;
            return Objects.equals(ingredient, key.ingredient)
                    && Objects.equals(nutrient, key.nutrient);
        }

        @Override
        public int hashCode() {
            return Objects.hash(ingredient, nutrient);
        }
    }
}
