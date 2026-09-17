package mk.finki.nutritioneer.service;

import lombok.RequiredArgsConstructor;
import mk.finki.nutritioneer.domain.Biometrics;
import mk.finki.nutritioneer.exception.ApiException;
import mk.finki.nutritioneer.repo.BiometricsRepository;
import mk.finki.nutritioneer.web.dto.BiometricsDtos.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BiometricsService {

    private final BiometricsRepository biometrics;
    private final CurrentUser currentUser;

    @Transactional
    public MeasurementView record(RecordRequest request) {
        String email = currentUser.email();

        // uq_biometrics_user_date allows one measurement per day, so a second
        // entry for the same date updates the first rather than failing.
        Biometrics entry = biometrics.findByOwnerEmailAndDate(email, request.date())
                .orElseGet(() -> Biometrics.builder()
                        .owner(currentUser.get())
                        .date(request.date())
                        .build());

        entry.setWeight(request.weight());
        entry.setHeight(request.height());
        entry.setAge(request.age());
        entry.setMuscleFatRatio(request.muscleFatRatio());

        Biometrics saved = biometrics.save(entry);
        return new MeasurementView(saved.getId(), saved.getDate(), saved.getWeight(),
                saved.getHeight(), saved.getMuscleFatRatio(), saved.getBmi(), null);
    }

    @Transactional(readOnly = true)
    public List<MeasurementView> history(String email) {
        List<Biometrics> rows = biometrics.findByOwnerEmailOrderByDate(email);
        List<MeasurementView> out = new ArrayList<>(rows.size());

        BigDecimal previousWeight = null;
        for (Biometrics b : rows) {
            BigDecimal change = (previousWeight == null || b.getWeight() == null)
                    ? null
                    : b.getWeight().subtract(previousWeight);
            out.add(new MeasurementView(b.getId(), b.getDate(), b.getWeight(),
                    b.getHeight(), b.getMuscleFatRatio(), b.getBmi(), change));
            if (b.getWeight() != null) {
                previousWeight = b.getWeight();
            }
        }
        return out;
    }

    @Transactional
    public void delete(Long id) {
        Biometrics entry = biometrics.findById(id)
                .orElseThrow(() -> ApiException.notFound("Measurement"));
        if (!entry.getOwner().getEmail().equals(currentUser.email())) {
            throw ApiException.forbidden("You can only delete your own measurements");
        }
        biometrics.delete(entry);
    }
}
