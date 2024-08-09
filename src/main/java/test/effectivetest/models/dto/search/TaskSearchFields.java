package test.effectivetest.models.dto.search;

import lombok.*;
import lombok.experimental.FieldDefaults;
import test.effectivetest.models.dto.enums.Priority;
import test.effectivetest.models.dto.enums.TaskStatus;
import test.effectivetest.models.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskSearchFields {
    String title;
    String description;
    Priority priority;
    TaskStatus status;
    LocalDateTime createdFrom;
    LocalDateTime createdTo;
    UUID contractor;
    UUID author;
}