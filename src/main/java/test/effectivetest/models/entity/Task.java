package test.effectivetest.models.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import test.effectivetest.models.dto.enums.Priority;
import test.effectivetest.models.dto.enums.TaskStatus;
import test.effectivetest.models.dto.requests.TaskCreateRequest;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    @Id
    UUID id;
    String title;
    String description;
    @Enumerated(EnumType.STRING)
    Priority priority;
    @Enumerated(EnumType.STRING)
    TaskStatus status;
    LocalDateTime created;
    LocalDateTime updated;
    @ManyToOne
    @JoinColumn(name = "contractor_id")
    User contractor;
    @ManyToOne
    @JoinColumn(name = "author_id")
    User author;

    public Task(TaskCreateRequest r, User contractor, User author) {
        this.id = UUID.randomUUID();
        this.title = r.title();
        this.description = r.description();
        this.priority = r.priority();
        this.status = TaskStatus.BACKLOG;
        this.created = LocalDateTime.now();
        this.contractor = contractor;
        this.author = author;
    }
}
