package com.spike.todo.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RestException {

    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;

    public RestException(String message, Throwable throwable, HttpStatus httpStatus) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
    }

}
