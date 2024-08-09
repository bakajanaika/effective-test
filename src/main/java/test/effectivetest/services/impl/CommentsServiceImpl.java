package test.effectivetest.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import test.effectivetest.models.dto.CommentDto;
import test.effectivetest.models.dto.requests.CommentRequest;
import test.effectivetest.models.entity.Comment;
import test.effectivetest.models.entity.User;
import test.effectivetest.repository.CommentRepository;
import test.effectivetest.repository.TaskRepository;
import test.effectivetest.services.CommentsService;
import test.effectivetest.services.UserService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {
    UserService userService;

    CommentRepository commentRepository;
    TaskRepository taskRepository;

    @Override
    public CommentDto createComment(CommentRequest commentRequest) {
        User currentUser = userService.getUserFromContext();
        return new CommentDto(commentRepository
                .save(new Comment(commentRequest.comment(), currentUser, taskRepository.findById(commentRequest.taskId()).orElseThrow())));
    }

    @Override
    public CommentDto updateComment(UUID commentId, CommentRequest commentRequest) {
        User currentUser = userService.getUserFromContext();
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (currentUser.getId().equals(comment.getUser().getId())) {
            comment.setComment(commentRequest.comment());
            comment.setUpdatedAt(LocalDateTime.now());
            return new CommentDto(commentRepository.save(comment));
        } else {
            throw new AccessDeniedException("User can not delete other users comments");
        }
    }

    @Override
    public void deleteComment(UUID commentId) {
        User currentUser = userService.getUserFromContext();
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (currentUser.getId().equals(comment.getUser().getId())) {
            commentRepository.deleteById(commentId);
        } else {
            throw new AccessDeniedException("User can not delete other users comments");
        }
    }
}
