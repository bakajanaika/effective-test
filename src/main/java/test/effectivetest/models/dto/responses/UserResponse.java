package test.effectivetest.models.dto.responses;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import test.effectivetest.models.entity.User;

import java.util.UUID;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class UserResponse {
    UUID id;
    String email;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }
}
