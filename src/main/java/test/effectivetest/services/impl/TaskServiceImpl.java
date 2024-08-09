package test.effectivetest.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import test.effectivetest.models.dto.CommentDto;
import test.effectivetest.models.dto.TaskDto;
import test.effectivetest.models.dto.requests.TaskCreateRequest;
import test.effectivetest.models.dto.requests.TaskUpdateRequest;
import test.effectivetest.models.dto.search.TaskSearchFields;
import test.effectivetest.models.dto.search.TaskSortFields;
import test.effectivetest.models.entity.Task;
import test.effectivetest.models.entity.User;
import test.effectivetest.repository.CommentRepository;
import test.effectivetest.repository.TaskRepository;
import test.effectivetest.repository.UserRepository;
import test.effectivetest.repository.specifications.TaskSpecification;
import test.effectivetest.services.TaskService;
import test.effectivetest.services.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    UserService userService;

    UserRepository userRepository;
    TaskRepository taskRepository;
    CommentRepository commentRepository;

    @Override
    public UUID createTask(TaskCreateRequest request) {
        User currentUser = userService.getUserFromContext();
        this.validateExistsContractorAndAuthor(currentUser.getId(), request.contractorId());
        User contractor = userRepository.findById(request.contractorId()).orElseThrow();
        Task task = taskRepository.save(new Task(request, contractor, currentUser));
        return task.getId();
    }

    @Override
    public UUID updateTask(TaskUpdateRequest request, UUID id) {
        User currentUser = userService.getUserFromContext();
        this.validateExistsContractorAndAuthor(currentUser.getId(), request.contractorId());
        User contractor = userRepository.findById(request.contractorId()).orElseThrow();
        Task task = taskRepository.findById(id).orElseThrow();
        task.setDescription(request.description());
        task.setPriority(request.priority());
        task.setContractor(contractor);
        task.setStatus(request.status());
        task.setUpdated(LocalDateTime.now());
        task.setTitle(request.title());
        return taskRepository.save(task).getId();
    }

    @Override
    public Page<TaskDto> searchTasks(TaskSearchFields searchFields, TaskSortFields sortFields, int page, int size) {
        Specification<Task> spec = TaskSpecification.filterTasks(searchFields);
        Sort sort = Sort.by(sortFields.isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC, sortFields.getSortBy());
        Pageable pageable = PageRequest.of(page, size, sort);
        return taskRepository.findAll(spec, pageable).map(task -> {
            List<CommentDto> comments = commentRepository.findAllByTaskId(task.getId()).stream().map(CommentDto::new).toList();
            return new TaskDto(task, comments);
        });
    }

    @Override
    public TaskDto getById(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow();
        List<CommentDto> comments = commentRepository.findAllByTaskId(task.getId()).stream().map(CommentDto::new).toList();
        return new TaskDto(task, comments);
    }

    private void validateExistsContractorAndAuthor(UUID contractorId, UUID authorId) {
        if (!userRepository.existsById(contractorId) || !userRepository.existsById(authorId)) {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
