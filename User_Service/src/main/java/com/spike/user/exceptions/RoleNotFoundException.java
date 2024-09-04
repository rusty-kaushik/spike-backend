package com.spike.user.exceptions;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String message) {
        super(message);
    }
    public RoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
