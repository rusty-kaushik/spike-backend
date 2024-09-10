package com.spike.SecureGate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {UnexpectedException.class})
    public ResponseEntity<Object> handleUnexpectedException(UnexpectedException ex) {
        return buildResponseEntityForExceptions(ex, HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<Object> buildResponseEntityForExceptions(RuntimeException ex, HttpStatus status) {
        RestException restException = new RestException(
                ex.getMessage(),
                ex.getCause(),
                status
        );
        return new ResponseEntity<>(restException, status);
    }

}