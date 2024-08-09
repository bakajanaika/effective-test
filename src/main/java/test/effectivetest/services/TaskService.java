package test.effectivetest.services;

import org.springframework.data.domain.Page;
import test.effectivetest.models.dto.TaskDto;
import test.effectivetest.models.dto.requests.TaskCreateRequest;
import test.effectivetest.models.dto.requests.TaskUpdateRequest;
import test.effectivetest.models.dto.search.TaskSearchFields;
import test.effectivetest.models.dto.search.TaskSortFields;

import java.util.UUID;

public interface TaskService {
    UUID createTask(TaskCreateRequest request);

    UUID updateTask(TaskUpdateRequest request, UUID id);

    Page<TaskDto> searchTasks(TaskSearchFields searchFields, TaskSortFields sortFields, int page, int size);

    TaskDto getById(UUID id);
}
