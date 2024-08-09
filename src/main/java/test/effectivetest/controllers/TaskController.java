package test.effectivetest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.effectivetest.models.dto.TaskDto;
import test.effectivetest.models.dto.requests.TaskCreateRequest;
import test.effectivetest.models.dto.requests.TaskSearchRequest;
import test.effectivetest.models.dto.requests.TaskUpdateRequest;
import test.effectivetest.services.TaskService;

import java.util.UUID;

@RestController
@RequestMapping(TaskController.PATH)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Контроллер для работы с задачами", description = TaskController.PATH)
public class TaskController {
    static final String PATH = "api/tasks";

    TaskService taskService;

    @GetMapping("{id}")
    @Operation(summary = "Получение задачи по идентификатору")
    public ResponseEntity<TaskDto> getTask(@PathVariable UUID id) {
        return new ResponseEntity<>(taskService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Создание задачи (назначитель задачи ставится создающий ее пользователь)")
    public ResponseEntity<UUID> createTask(@RequestBody TaskCreateRequest request) {
        UUID taskId = taskService.createTask(request);
        return new ResponseEntity<>(taskId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение задачи (назначителя нельзя изменить)")
    public ResponseEntity<UUID> updateTask(@PathVariable UUID id, @RequestBody TaskUpdateRequest request) {
        UUID taskId = taskService.updateTask(request, id);
        return new ResponseEntity<>(taskId, HttpStatus.OK);
    }

    @PostMapping("/search")
    @Operation(summary = "Поиск по всем задачам с сортировкой и фильтрами (смотреть схему(поля сортировки обязательные))")
    public ResponseEntity<Page<TaskDto>> searchTasks(@RequestBody TaskSearchRequest searchRequest,
                                                     @RequestParam(required = false, defaultValue = "0") int page,
                                                     @RequestParam(required = false, defaultValue = "25") int size) {

        Page<TaskDto> result = taskService
                .searchTasks(searchRequest.getTaskSearchFields(),
                        searchRequest.getTaskSortFields(),
                        page,
                        size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
