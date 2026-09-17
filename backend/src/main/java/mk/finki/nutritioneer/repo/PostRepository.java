package mk.finki.nutritioneer.repo;

import mk.finki.nutritioneer.domain.Post;
import mk.finki.nutritioneer.domain.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByStatusAndIsPrivateFalseOrderByCreatedAtDesc(PostStatus status,
                                                                 Pageable pageable);

    List<Post> findByAuthorEmailOrderByCreatedAtDesc(String email);

    Optional<Post> findByRecipeId(Long recipeId);

    boolean existsByRecipeId(Long recipeId);

    /**
     * the feed filtered by a dietary restriction type, with
     * recipes carrying an unwanted allergen excluded. Native, because the pair
     * of correlated EXISTS clauses over a link table is far clearer in SQL
     * than in JPQL - and this is the exact statement verified against the
     * seed data.
     */
    @Query(value = """
            SELECT p.*
            FROM post p
            JOIN recipe r ON r.id = p.recipe_id
            WHERE p.status = 'published'
              AND p.is_private = FALSE
              AND EXISTS (
                  SELECT 1 FROM recipe_contains_restriction rcr
                  JOIN restriction res ON res.id = rcr.restriction_id
                  WHERE rcr.recipe_id = r.id AND res.type::text = :type)
              AND NOT EXISTS (
                  SELECT 1 FROM recipe_contains_restriction rcr
                  JOIN restriction res ON res.id = rcr.restriction_id
                  WHERE rcr.recipe_id = r.id AND res.description IN (:avoid))
            ORDER BY r.kcal_sum / r.servings
            """, nativeQuery = true)
    List<Post> feedByRestriction(@Param("type") String type,
                                 @Param("avoid") List<String> avoid);
}
