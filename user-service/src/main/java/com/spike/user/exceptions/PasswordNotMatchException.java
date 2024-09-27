package com.spike.user.exceptions;

import lombok.Getter;

@Getter
public class PasswordNotMatchException extends RuntimeException {

    private final String error;

    public PasswordNotMatchException(String error, String message) {
        super(message);
        this.error = error;
    }

}