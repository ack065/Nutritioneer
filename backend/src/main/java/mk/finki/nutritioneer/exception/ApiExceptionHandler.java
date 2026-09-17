package mk.finki.nutritioneer.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Turns exceptions into a single JSON shape. Database constraint violations are
 * translated rather than leaked: every CHECK and UNIQUE in the DDL is a business
 * rule, so a violation is a 409 with a readable message, not a 500.
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, Object>> handleApi(ApiException ex) {
        return build(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) {
        return build(HttpStatus.UNAUTHORIZED, "Invalid email or password");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> body = base(HttpStatus.BAD_REQUEST, "Validation failed");
        Map<String, String> fields = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> fields.put(e.getField(), e.getDefaultMessage()));
        body.put("fields", fields);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraint(DataIntegrityViolationException ex) {
        String raw = ex.getMostSpecificCause().getMessage();
        return build(HttpStatus.CONFLICT, translate(raw));
    }

    private String translate(String raw) {
        if (raw == null) {
            return "The request violates a database constraint";
        }
        if (raw.contains("uq_post_recipe")) {
            return "This recipe has already been shared as a post";
        }
        if (raw.contains("uq_user_username")) {
            return "That username is already taken";
        }
        if (raw.contains("uq_biometrics_user_date")) {
            return "A measurement for that date already exists";
        }
        if (raw.contains("uq_nutrient_description")) {
            return "That nutrient already exists in the catalogue";
        }
        if (raw.contains("ck_recipe_servings")) {
            return "Servings must be greater than zero";
        }
        if (raw.contains("ck_user_email_format")) {
            return "That email address is not valid";
        }
        log.warn("Unmapped constraint violation: {}", raw);
        return "The request violates a database constraint";
    }

    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(base(status, message));
    }

    private Map<String, Object> base(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return body;
    }
}
