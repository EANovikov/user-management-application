package com.xevgnov.UserManagementApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalRoleException extends UserManagementException {
    public IllegalRoleException() {
    }

    public IllegalRoleException(String message) {
        super(message);
    }
}
