package test.effectivetest.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import test.effectivetest.exceptions.EmailAlreadyExists;
import test.effectivetest.exceptions.InvalidPasswordException;
import test.effectivetest.models.dto.requests.LoginRequest;
import test.effectivetest.models.dto.requests.RegisterRequest;
import test.effectivetest.models.dto.responses.LoginResponse;
import test.effectivetest.models.dto.responses.UserResponse;
import test.effectivetest.models.entity.User;
import test.effectivetest.repository.UserRepository;
import test.effectivetest.services.UserService;
import test.effectivetest.utils.JwtUtil;

import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    PasswordEncoder encoder;
    UserDetailsImpl userDetailsService;
    JwtUtil jwtUtil;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExists();
        } else {
            return new UserResponse(userRepository.save(new User(request, encoder)));
        }
    }

    @Override
    public ResponseEntity<?> login(LoginRequest request) {
        try {
            User user = userRepository.findByEmail(request.email());
            if (user == null || !encoder.matches(request.password(), user.getPassword())) {
                throw new InvalidPasswordException();
            }
            String token = jwtUtil.generateToken(userDetailsService.loadUserByUsername(user.getEmail()));
            return ResponseEntity.ok().body(new LoginResponse(token, user.getEmail()));
        } catch (IllegalArgumentException ex) {
            logger.error("Unexpected error occurred during authentication", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred"));
        }
    }

    @Override
    public User getUserFromContext() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
