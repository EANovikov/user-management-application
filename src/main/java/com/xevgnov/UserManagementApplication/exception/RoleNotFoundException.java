package com.xevgnov.UserManagementApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RoleNotFoundException extends UserManagementException{

    public RoleNotFoundException() {
        super();
    }

    public RoleNotFoundException(String message) {
        super(message);
    }
}
