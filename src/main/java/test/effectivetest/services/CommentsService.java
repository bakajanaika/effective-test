package test.effectivetest.services;

import test.effectivetest.models.dto.CommentDto;
import test.effectivetest.models.dto.requests.CommentRequest;

import java.util.UUID;

public interface CommentsService {
    CommentDto createComment(CommentRequest commentRequest);

    CommentDto updateComment(UUID commentId, CommentRequest commentRequest);

    void deleteComment(UUID commentId);
}
