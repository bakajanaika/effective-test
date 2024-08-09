package test.effectivetest.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;
import test.effectivetest.models.dto.CommentDto;
import test.effectivetest.models.dto.requests.CommentRequest;
import test.effectivetest.models.entity.Comment;
import test.effectivetest.models.entity.Task;
import test.effectivetest.models.entity.User;
import test.effectivetest.repository.CommentRepository;
import test.effectivetest.repository.TaskRepository;
import test.effectivetest.services.UserService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommentsServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private CommentsServiceImpl commentsService;

    private User currentUser;
    private Task task;
    private Comment comment;

    @BeforeEach
    void setUp() {
         MockitoAnnotations.openMocks(this);
        currentUser = new User(UUID.randomUUID(), "username", "password");
        task = new Task();
        task.setId(UUID.randomUUID());
        comment = new Comment("Test comment", currentUser, task);
        comment.setId(UUID.randomUUID());
    }

    @Test
    void createComment() {
        CommentRequest commentRequest = new CommentRequest(task.getId(), "Test comment");

        when(userService.getUserFromContext()).thenReturn(currentUser);
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDto result = commentsService.createComment(commentRequest);

        assertNotNull(result);
        assertEquals(comment.getComment(), result.getComment());
        assertEquals(comment.getUser().getId(), result.getUser().getId());
        assertEquals(comment.getTask().getId(), result.getTask());

        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void updateComment_whenUserIsOwner() {
        CommentRequest commentRequest = new CommentRequest(task.getId(), "Updated comment");

        when(userService.getUserFromContext()).thenReturn(currentUser);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDto result = commentsService.updateComment(comment.getId(), commentRequest);

        assertNotNull(result);
        assertEquals(commentRequest.comment(), result.getComment());

        verify(commentRepository).save(comment);
    }

    @Test
    void updateComment_whenUserIsNotOwner() {
        CommentRequest commentRequest = new CommentRequest(task.getId(), "Updated comment");

        User anotherUser = new User(UUID.randomUUID(), "anotherUser", "password");

        when(userService.getUserFromContext()).thenReturn(anotherUser);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () ->
                commentsService.updateComment(comment.getId(), commentRequest));

        assertEquals("User can not delete other users comments", exception.getMessage());

        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void deleteComment_whenUserIsOwner() {
        when(userService.getUserFromContext()).thenReturn(currentUser);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        commentsService.deleteComment(comment.getId());

        verify(commentRepository).deleteById(comment.getId());
    }

    @Test
    void deleteComment_whenUserIsNotOwner() {
        User anotherUser = new User(UUID.randomUUID(), "anotherUser", "password");

        when(userService.getUserFromContext()).thenReturn(anotherUser);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () ->
                commentsService.deleteComment(comment.getId()));

        assertEquals("User can not delete other users comments", exception.getMessage());

        verify(commentRepository, never()).deleteById(any(UUID.class));
    }
}
