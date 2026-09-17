package mk.finki.nutritioneer.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
@Table(name = "biometrics")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Biometrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    /** kg */
    @Column(name = "weight")
    private BigDecimal weight;

    /** cm */
    @Column(name = "height")
    private BigDecimal height;

    @Column(name = "age")
    private BigDecimal age;

    @Column(name = "muscle_fat_ratio")
    private BigDecimal muscleFatRatio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_email", nullable = false)
    private AppUser owner;

    @Transient
    public BigDecimal getBmi() {
        if (weight == null || height == null || height.signum() == 0) {
            return null;
        }
        BigDecimal metres = height.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        return weight.divide(metres.multiply(metres), 1, RoundingMode.HALF_UP);
    }
}
