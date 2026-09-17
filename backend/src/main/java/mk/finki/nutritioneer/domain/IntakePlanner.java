package mk.finki.nutritioneer.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "intake_planner")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class IntakePlanner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "notes")
    private String notes;

    @Column(name = "kcal", nullable = false)
    private BigDecimal kcal;

    @Column(name = "is_consumed", nullable = false)
    private boolean isConsumed;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_email", nullable = false)
    private AppUser owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "intake_planner_save_to_list_post",
            joinColumns = @JoinColumn(name = "planner_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    @Builder.Default
    private Set<Post> savedPosts = new LinkedHashSet<>();

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
