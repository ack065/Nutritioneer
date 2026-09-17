package mk.finki.nutritioneer.service;

import lombok.RequiredArgsConstructor;
import mk.finki.nutritioneer.domain.IntakePlanner;
import mk.finki.nutritioneer.domain.Post;
import mk.finki.nutritioneer.exception.ApiException;
import mk.finki.nutritioneer.repo.IntakePlannerRepository;
import mk.finki.nutritioneer.repo.PostRepository;
import mk.finki.nutritioneer.web.dto.PlannerDtos.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlannerService {

    private final IntakePlannerRepository planner;
    private final PostRepository posts;
    private final CurrentUser currentUser;

    @Transactional
    public PlannerEntryView log(LogMealRequest request) {
        boolean hasMeals = request.postIds() != null && !request.postIds().isEmpty();

        IntakePlanner entry = IntakePlanner.builder()
                .owner(currentUser.get())
                .dateTime(request.dateTime() == null ? LocalDateTime.now() : request.dateTime())
                .notes(request.notes())
                .isConsumed(request.consumed())
                // An entry with no attached meal keeps whatever the user typed;
                // one with meals has its energy derived and the manual figure ignored.
                .kcal(hasMeals || request.manualKcal() == null
                        ? BigDecimal.ZERO
                        : request.manualKcal())
                .build();

        if (hasMeals) {
            Set<Post> meals = new LinkedHashSet<>(posts.findAllById(request.postIds()));
            if (meals.size() != request.postIds().size()) {
                throw ApiException.badRequest("One or more of the selected meals does not exist");
            }
            entry.setSavedPosts(meals);
        }

        IntakePlanner saved = planner.save(entry);

        if (hasMeals) {
            planner.recalculateKcal(saved.getId());
        }

        return view(planner.findById(saved.getId()).orElseThrow());
    }

    @Transactional(readOnly = true)
    public List<PlannerEntryView> mine() {
        return planner.findByOwnerEmailOrderByDateTimeDesc(currentUser.email())
                .stream().map(this::view).toList();
    }

    @Transactional(readOnly = true)
    public List<DailyTotalView> dailyTotals(String email, LocalDate from, LocalDate to) {
        return planner.dailyIntake(email, from.atStartOfDay(), to.atStartOfDay()).stream()
                .map(r -> new DailyTotalView(r.getDay(), r.getKcalConsumed(), r.getMealsLogged()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NutrientTotalView> macroTotals(String email, LocalDate from, LocalDate to) {
        return planner.macroTotals(email, from.atStartOfDay(), to.atStartOfDay()).stream()
                .map(r -> new NutrientTotalView(r.getNutrient(), r.getUnit(), r.getTotal()))
                .toList();
    }

    @Transactional
    public void markConsumed(Long id, boolean consumed) {
        IntakePlanner entry = planner.findById(id)
                .orElseThrow(() -> ApiException.notFound("Planner entry"));
        if (!entry.getOwner().getEmail().equals(currentUser.email())) {
            throw ApiException.forbidden("You can only change your own planner");
        }
        entry.setConsumed(consumed);
    }

    private PlannerEntryView view(IntakePlanner e) {
        List<String> meals = e.getSavedPosts().stream()
                .map(p -> p.getRecipe() == null ? "(photo post)" : p.getRecipe().getName())
                .toList();
        return new PlannerEntryView(e.getId(), e.getDateTime(), e.getNotes(),
                e.getKcal(), e.isConsumed(), meals);
    }
}
