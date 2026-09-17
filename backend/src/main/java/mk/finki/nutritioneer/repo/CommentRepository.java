package mk.finki.nutritioneer.repo;

import mk.finki.nutritioneer.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByCreatedAt(Long postId);
}
