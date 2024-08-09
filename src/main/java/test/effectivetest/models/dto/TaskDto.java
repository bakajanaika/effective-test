package test.effectivetest.models.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import test.effectivetest.models.dto.enums.Priority;
import test.effectivetest.models.dto.enums.TaskStatus;
import test.effectivetest.models.dto.responses.UserResponse;
import test.effectivetest.models.entity.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDto {
    UUID id;
    String title;
    String description;
    Priority priority;
    TaskStatus status;
    LocalDateTime created;
    LocalDateTime updated;
    UserResponse contractor;
    UserResponse author;
    List<CommentDto> comments;

    public TaskDto(Task task, List<CommentDto> comments) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.priority = task.getPriority();
        this.status = task.getStatus();
        this.created = task.getCreated();
        this.updated = task.getUpdated();
        this.contractor = new UserResponse(task.getContractor());
        this.author = new UserResponse(task.getAuthor());
        this.comments = comments;
    }
}
