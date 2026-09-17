package mk.finki.nutritioneer.repo;

import mk.finki.nutritioneer.domain.Restriction;
import mk.finki.nutritioneer.domain.RestrictionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestrictionRepository extends JpaRepository<Restriction, Long> {
    List<Restriction> findByType(RestrictionType type);
}
