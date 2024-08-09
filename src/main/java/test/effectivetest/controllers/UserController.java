package test.effectivetest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.effectivetest.models.dto.requests.LoginRequest;
import test.effectivetest.models.dto.requests.RegisterRequest;
import test.effectivetest.services.UserService;

@RestController
@RequestMapping(UserController.PATH)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Регистрация и авторизация пользователей", description = UserController.PATH)
public class UserController {
    static final String PATH = "api/users";

    UserService userService;

    @PostMapping("login")
    @Operation(summary = "Получение токена с валидацией")
    ResponseEntity<?> auth(@Validated @RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @PostMapping("register")
    @Operation(summary = "Регистрация с валидацией (паттерн для пароля '^[a-zA-Z0-9\\-!=+)(*?:;$#@/\\\\]{8,}$'")
    ResponseEntity<?> register(@Validated @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

}
