package mk.finki.nutritioneer.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mk.finki.nutritioneer.domain.RestrictionType;
import mk.finki.nutritioneer.service.PostService;
import mk.finki.nutritioneer.web.dto.PostDtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "Feed and posts", description = "UseCase1004, UseCase3003")
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Share one of your recipes as a post (1:1 - a recipe can be shared once)")
    public PostView share(@Valid @RequestBody SharePostRequest request) {
        return postService.share(request);
    }

    @GetMapping("/feed")
    @Operation(summary = "The public feed, newest first")
    public List<PostView> feed(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int size) {
        return postService.feed(page, size);
    }

    @GetMapping("/mine")
    @Operation(summary = "Posts created by the signed-in user, any status")
    public List<PostView> mine() {
        return postService.mine();
    }

    @GetMapping("/feed/by-restriction")
    @Operation(summary = "Feed filtered by a dietary restriction, excluding unwanted allergens")
    public List<PostView> byRestriction(@RequestParam RestrictionType type,
                                        @RequestParam(required = false) List<String> avoid) {
        return postService.feedByRestriction(type, avoid);
    }

    @PostMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Comment on a post")
    public CommentView comment(@PathVariable Long id,
                               @Valid @RequestBody CommentRequest request) {
        return postService.comment(id, request);
    }

    @GetMapping("/{id}/comments")
    @Operation(summary = "Read the comment thread of a post")
    public List<CommentView> comments(@PathVariable Long id) {
        return postService.comments(id);
    }
}
