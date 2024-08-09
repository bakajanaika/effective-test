package test.effectivetest.models.dto.requests;

import jakarta.validation.constraints.NotNull;
import test.effectivetest.models.dto.enums.Priority;
import test.effectivetest.models.dto.enums.TaskStatus;

import java.util.UUID;

public record TaskUpdateRequest(
        @NotNull String title,
        String description,
        @NotNull Priority priority,
        @NotNull TaskStatus status,
        @NotNull UUID contractorId
) {
}
