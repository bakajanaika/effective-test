package test.effectivetest.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends BaseException {
    public InvalidPasswordException() {
        super(HttpStatus.UNAUTHORIZED, "Invalid Password");
    }
}
