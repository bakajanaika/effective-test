package test.effectivetest.models.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import test.effectivetest.models.dto.responses.UserResponse;
import test.effectivetest.models.entity.Comment;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {
    UUID id;
    String comment;
    UserResponse user;
    UUID task;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.user = new UserResponse(comment.getUser());
        this.task = comment.getTask().getId();
    }
}
