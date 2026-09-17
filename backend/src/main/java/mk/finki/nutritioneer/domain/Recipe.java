package mk.finki.nutritioneer.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "recipe")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "guide", nullable = false)
    private String guide;

    @Column(name = "kcal_sum", nullable = false)
    private BigDecimal kcalSum;

    @Column(name = "servings", nullable = false)
    private BigDecimal servings;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_email", nullable = false)
    private AppUser owner;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<RecipeIngredient> ingredients = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "recipe_contains_restriction",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "restriction_id"))
    @Builder.Default
    private Set<Restriction> restrictions = new LinkedHashSet<>();

    @Transient
    public BigDecimal getKcalPerServing() {
        if (kcalSum == null || servings == null || servings.signum() == 0) {
            return BigDecimal.ZERO;
        }
        return kcalSum.divide(servings, 2, RoundingMode.HALF_UP);
    }
}
