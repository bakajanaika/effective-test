package test.effectivetest.models.dto.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;
import test.effectivetest.models.dto.search.TaskSearchFields;
import test.effectivetest.models.dto.search.TaskSortFields;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskSearchRequest {
    TaskSearchFields taskSearchFields;
    TaskSortFields taskSortFields;
}
