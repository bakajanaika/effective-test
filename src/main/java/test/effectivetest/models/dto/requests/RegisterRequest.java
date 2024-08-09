package test.effectivetest.models.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        @NotNull @Email
        String email,
        @NotNull @Pattern(regexp = "^[a-zA-Z0-9\\-!=+)(*?:;$#@/\\\\]{8,}$", message = "Invalid password")
        String password
) {
}
