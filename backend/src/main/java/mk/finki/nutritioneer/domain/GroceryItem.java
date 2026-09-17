package mk.finki.nutritioneer.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "grocery_list_single_add_ingredient")
@IdClass(GroceryItem.Key.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GroceryItem {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "list_id", nullable = false)
    private GroceryList list;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ingredient_name", nullable = false)
    private Ingredient ingredient;

    @Column(name = "buy_quantity", nullable = false)
    private BigDecimal buyQuantity;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class Key implements Serializable {
        private Long list;
        private String ingredient;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key key)) return false;
            return Objects.equals(list, key.list)
                    && Objects.equals(ingredient, key.ingredient);
        }

        @Override
        public int hashCode() {
            return Objects.hash(list, ingredient);
        }
    }
}
