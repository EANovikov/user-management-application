package com.xevgnov.UserManagementApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends UserManagementException {
    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
