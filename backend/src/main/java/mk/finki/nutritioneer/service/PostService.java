package mk.finki.nutritioneer.service;

import lombok.RequiredArgsConstructor;
import mk.finki.nutritioneer.domain.*;
import mk.finki.nutritioneer.exception.ApiException;
import mk.finki.nutritioneer.repo.*;
import mk.finki.nutritioneer.web.dto.PostDtos.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository posts;
    private final RecipeRepository recipes;
    private final CommentRepository comments;
    private final CurrentUser currentUser;

    @Transactional
    public PostView share(SharePostRequest request) {
        AppUser author = currentUser.get();
        Recipe recipe = recipes.findById(request.recipeId())
                .orElseThrow(() -> ApiException.notFound("Recipe"));

        if (!recipe.getOwner().getEmail().equals(author.getEmail())) {
            throw ApiException.forbidden("You can only share your own recipes");
        }
        // uq_post_recipe would reject this anyway; checking first gives a clearer message.
        if (posts.existsByRecipeId(recipe.getId())) {
            throw ApiException.conflict("This recipe has already been shared as a post");
        }

        Post post = Post.builder()
                .recipe(recipe)
                .author(author)
                .isPrivate(request.isPrivate())
                .isFavourite(request.isFavourite())
                .status(request.status() == null
                        ? PostStatus.published
                        : PostStatus.valueOf(request.status()))
                .build();

        return view(posts.save(post));
    }

    @Transactional(readOnly = true)
    public List<PostView> feed(int page, int size) {
        return posts.findByStatusAndIsPrivateFalseOrderByCreatedAtDesc(
                        PostStatus.published, PageRequest.of(page, size))
                .map(this::view)
                .getContent();
    }

    @Transactional(readOnly = true)
    public List<PostView> mine() {
        return posts.findByAuthorEmailOrderByCreatedAtDesc(currentUser.email())
                .stream().map(this::view).toList();
    }

    /** UseCase3003 - the feed filtered by dietary restriction. */
    @Transactional(readOnly = true)
    public List<PostView> feedByRestriction(RestrictionType type, List<String> avoid) {
        List<String> exclusions = (avoid == null || avoid.isEmpty())
                ? List.of("__none__")   // IN () is not valid SQL, so pass a value that matches nothing
                : avoid;
        return posts.feedByRestriction(type.name(), exclusions)
                .stream().map(this::view).toList();
    }

    @Transactional
    public CommentView comment(Long postId, CommentRequest request) {
        Post post = posts.findById(postId)
                .orElseThrow(() -> ApiException.notFound("Post"));

        Comment comment = comments.save(Comment.builder()
                .post(post)
                .author(currentUser.get())
                .description(request.description())
                .build());

        return new CommentView(comment.getId(), comment.getDescription(),
                comment.getAuthor().getUsername(), comment.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<CommentView> comments(Long postId) {
        return comments.findByPostIdOrderByCreatedAt(postId).stream()
                .map(c -> new CommentView(c.getId(), c.getDescription(),
                        c.getAuthor().getUsername(), c.getCreatedAt()))
                .toList();
    }

    /** UseCase2003 - moderation. Archiving keeps the post for its author. */
    @Transactional
    public void setStatus(Long postId, PostStatus status) {
        Post post = posts.findById(postId)
                .orElseThrow(() -> ApiException.notFound("Post"));
        post.setStatus(status);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        if (!comments.existsById(commentId)) {
            throw ApiException.notFound("Comment");
        }
        comments.deleteById(commentId);
    }

    private PostView view(Post p) {
        Recipe r = p.getRecipe();
        return new PostView(
                p.getId(),
                r == null ? null : r.getId(),
                r == null ? null : r.getName(),
                r == null ? BigDecimal.ZERO : r.getKcalPerServing(),
                p.getAuthor().getUsername(),
                p.getStatus().name(),
                p.isPrivate(),
                p.isFavourite(),
                p.getCreatedAt(),
                comments.findByPostIdOrderByCreatedAt(p.getId()).size());
    }
}
