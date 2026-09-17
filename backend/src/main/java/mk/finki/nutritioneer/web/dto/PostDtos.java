package mk.finki.nutritioneer.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class PostDtos {

    private PostDtos() {
    }

    public record SharePostRequest(
            @NotNull Long recipeId,
            boolean isPrivate,
            boolean isFavourite,
            String status) {
    }

    public record PostView(
            Long id,
            Long recipeId,
            String recipeName,
            BigDecimal kcalPerServing,
            String authorUsername,
            String status,
            boolean isPrivate,
            boolean isFavourite,
            LocalDateTime createdAt,
            long commentCount) {
    }

    public record CommentRequest(@NotBlank String description) {
    }

    public record CommentView(
            Long id,
            String description,
            String authorUsername,
            LocalDateTime createdAt) {
    }
}
