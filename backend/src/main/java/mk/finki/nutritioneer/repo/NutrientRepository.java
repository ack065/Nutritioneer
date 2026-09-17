package mk.finki.nutritioneer.repo;

import mk.finki.nutritioneer.domain.Nutrient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NutrientRepository extends JpaRepository<Nutrient, Long> {
    Optional<Nutrient> findByDescription(String description);
}
