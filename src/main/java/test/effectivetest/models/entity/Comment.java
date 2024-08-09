package test.effectivetest.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    UUID id;
    String comment;
    @ManyToOne
    User user;
    @ManyToOne
    Task task;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public Comment(String comment, User user, Task task) {
        this.id = UUID.randomUUID();
        this.comment = comment;
        this.user = user;
        this.task = task;
        this.createdAt = LocalDateTime.now();
    }
}
