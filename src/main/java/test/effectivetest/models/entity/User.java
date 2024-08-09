package test.effectivetest.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import test.effectivetest.models.dto.requests.RegisterRequest;

import java.util.UUID;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    UUID id;
    String email;
    String password;

    public User(RegisterRequest request, PasswordEncoder encoder) {
        this.id = UUID.randomUUID();
        this.email = request.email();
        this.password = encoder.encode(request.password());
    }
}
