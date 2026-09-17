package mk.finki.nutritioneer.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     Не е детално тестирано и имплементирано додавање на media
     - ќе се додаде накнадно
     */
    @JdbcTypeCode(SqlTypes.VARBINARY)
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "media", columnDefinition = "bytea")
    private byte[] media;

    @Column(name = "media_type")
    private String mediaType;

    @Column(name = "media_name")
    private String mediaName;

    @Column(name = "is_private", nullable = false)
    private boolean isPrivate;

    @Column(name = "is_favourite", nullable = false)
    private boolean isFavourite;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false, columnDefinition = "post_status")
    private PostStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private AppUser author;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id", unique = true)
    private Recipe recipe;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (status == null) {
            status = PostStatus.draft;
        }
    }
}
