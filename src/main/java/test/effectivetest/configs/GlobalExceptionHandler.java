package test.effectivetest.configs;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import test.effectivetest.exceptions.EmailAlreadyExists;
import test.effectivetest.exceptions.InvalidPasswordException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidPasswordException.class)
    public ErrorResponse invalidPassword(InvalidPasswordException e) {
        return this.getErrorResponse(e.getHttpStatus(), e);
    }

    @ExceptionHandler(EmailAlreadyExists.class)
    public ErrorResponse emailAlreadyExists(EmailAlreadyExists e) {
        return this.getErrorResponse(e.getHttpStatus(), e);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorResponse userNameNotFound(UsernameNotFoundException e) {
        return this.getErrorResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse elementNotFound(NoSuchElementException e) {
        return this.getErrorResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse accessDenied(AccessDeniedException e) {
        return this.getErrorResponse(HttpStatus.FORBIDDEN, e);
    }

    private ErrorResponse getErrorResponse(HttpStatus status, Exception e) {
        return ErrorResponse.builder(e, status, e.getMessage())
                .build();
    }
}
