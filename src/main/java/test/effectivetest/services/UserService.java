package test.effectivetest.services;

import org.springframework.http.ResponseEntity;
import test.effectivetest.models.dto.requests.LoginRequest;
import test.effectivetest.models.dto.requests.RegisterRequest;
import test.effectivetest.models.dto.responses.UserResponse;
import test.effectivetest.models.entity.User;

public interface UserService {
    UserResponse register(RegisterRequest request);

    ResponseEntity<?> login(LoginRequest request);

    User getUserFromContext();
}
