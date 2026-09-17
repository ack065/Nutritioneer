package mk.finki.nutritioneer.repo;

import mk.finki.nutritioneer.domain.Biometrics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BiometricsRepository extends JpaRepository<Biometrics, Long> {

    List<Biometrics> findByOwnerEmailOrderByDate(String email);

    Optional<Biometrics> findByOwnerEmailAndDate(String email, LocalDate date);
}
