package test.effectivetest.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.effectivetest.models.dto.enums.Priority;
import test.effectivetest.models.dto.enums.TaskStatus;
import test.effectivetest.models.dto.requests.TaskCreateRequest;
import test.effectivetest.models.dto.requests.TaskUpdateRequest;
import test.effectivetest.models.entity.Task;
import test.effectivetest.models.entity.User;
import test.effectivetest.repository.TaskRepository;
import test.effectivetest.repository.UserRepository;
import test.effectivetest.services.UserService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @Mock
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl testService;

    @Test
    void createTask() {
        User current = new User(UUID.randomUUID(), "author", "password");
        User contractor = new User(UUID.randomUUID(), "contractor", "password");
        TaskCreateRequest taskCreateRequest = new TaskCreateRequest("Title", "Description", Priority.HIGH, contractor.getId());
        Task task = new Task(taskCreateRequest, contractor, current);

        when(userService.getUserFromContext()).thenReturn(current);
        when(userRepository.existsById(current.getId())).thenReturn(true);
        when(userRepository.existsById(contractor.getId())).thenReturn(true);
        when(userRepository.findById(contractor.getId())).thenReturn(Optional.of(contractor));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        testService.createTask(taskCreateRequest);

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void updateTask() {
        UUID taskId = UUID.randomUUID();
        User currentUser = new User(UUID.randomUUID(), "currentUser", "password");
        User contractor = new User(UUID.randomUUID(), "contractor", "password");

        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest(
                "Updated Title",
                "Updated Description",
                Priority.MID,
                TaskStatus.IN_PROCESS,
                contractor.getId()
        );

        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setTitle("Old Title");
        existingTask.setDescription("Old Description");
        existingTask.setPriority(Priority.LOW);
        existingTask.setStatus(TaskStatus.BACKLOG);
        existingTask.setAuthor(currentUser);
        existingTask.setContractor(contractor);

        when(userService.getUserFromContext()).thenReturn(currentUser);
        when(userRepository.existsById(currentUser.getId())).thenReturn(true);
        when(userRepository.existsById(contractor.getId())).thenReturn(true);
        when(userRepository.findById(contractor.getId())).thenReturn(Optional.of(contractor));
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UUID response = testService.updateTask(taskUpdateRequest, taskId);

        assertEquals(taskId, response);
        assertEquals("Updated Title", existingTask.getTitle());
        assertEquals("Updated Description", existingTask.getDescription());
        assertEquals(Priority.MID, existingTask.getPriority());
        assertEquals(TaskStatus.IN_PROCESS, existingTask.getStatus());
        assertEquals(contractor, existingTask.getContractor());
        assertNotNull(existingTask.getUpdated());

        verify(taskRepository).save(existingTask);
    }

}