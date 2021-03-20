package com.xevgnov.UserManagementApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserManagementException extends RuntimeException {

    public UserManagementException() {
    }

    public UserManagementException(String message) {
        super(message);
    }

    public UserManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserManagementException(Throwable cause) {
        super(cause);
    }

    public UserManagementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
