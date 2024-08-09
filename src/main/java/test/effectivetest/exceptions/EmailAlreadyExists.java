package test.effectivetest.exceptions;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExists extends BaseException {
    public EmailAlreadyExists() {
        super(HttpStatus.BAD_REQUEST, "Email already exists");
    }
}
