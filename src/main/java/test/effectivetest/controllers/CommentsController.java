package test.effectivetest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.effectivetest.models.dto.CommentDto;
import test.effectivetest.models.dto.requests.CommentRequest;
import test.effectivetest.services.CommentsService;

import java.util.UUID;

@RestController
@RequestMapping(CommentsController.PATH)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Контроллер для работы с комментариями", description = CommentsController.PATH)
public class CommentsController {
    static final String PATH = "api/comments";

    CommentsService commentsService;

    @PostMapping()
    @Operation(summary = "Добавление комментария к существующей задаче")
    public ResponseEntity<CommentDto> addComment(@RequestBody @Validated CommentRequest request) {
        return new ResponseEntity<>(commentsService.createComment(request), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @Operation(summary = "Изменение комментария к существующей задаче с проверкой на то, что комментарий принадлежит пользователю")
    public ResponseEntity<CommentDto> updateComment(@RequestBody @Validated CommentRequest request, UUID id) {
        return new ResponseEntity<>(commentsService.updateComment(id, request), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление комментария с проверкой на то, что комментарий принадлежит пользователю")
    public void deleteComment(@PathVariable UUID id) {
        commentsService.deleteComment(id);
    }
}
