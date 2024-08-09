package test.effectivetest.models.dto.requests;

import jakarta.validation.constraints.NotNull;
import test.effectivetest.models.dto.enums.Priority;

import java.util.UUID;

public record TaskCreateRequest(
        @NotNull String title,
        String description,
        @NotNull Priority priority,
        @NotNull UUID contractorId
) {
}
