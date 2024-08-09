package test.effectivetest.models.dto.requests;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CommentRequest(
        @NotNull UUID taskId,
        @NotNull String comment
) {
}
