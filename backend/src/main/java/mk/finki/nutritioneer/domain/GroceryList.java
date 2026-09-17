package mk.finki.nutritioneer.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "grocery_list")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GroceryList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "notes")
    private String notes;

    @Column(name = "is_bought", nullable = false)
    private boolean isBought;

    @Column(name = "kcal", nullable = false)
    private BigDecimal kcal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_email", nullable = false)
    private AppUser owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "grocery_list_bulk_add_ingredient",
            joinColumns = @JoinColumn(name = "list_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    @Builder.Default
    private Set<Recipe> bulkRecipes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<GroceryItem> singleItems = new LinkedHashSet<>();

    @PrePersist
    void onCreate() {
        if (dateTime == null) {
            dateTime = LocalDateTime.now();
        }
        if (kcal == null) {
            kcal = BigDecimal.ZERO;
        }
    }
}
