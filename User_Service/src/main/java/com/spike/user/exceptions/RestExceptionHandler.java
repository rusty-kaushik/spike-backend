package com.spike.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleEmployeeNotFoundException(UserNotFoundException ex) {
        return buildResponseEntityForExceptions(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UnexpectedException.class})
    public ResponseEntity<Object> handleUnexpectedException(UnexpectedException ex) {
        return buildResponseEntityForExceptions(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {DepartmentNotFoundException.class})
    public ResponseEntity<Object> handleDepartmentNotFoundException(DepartmentNotFoundException ex) {
        return buildResponseEntityForExceptions(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {PasswordNotMatchException.class})
    public ResponseEntity<Object> handlePasswordNotMatchException(PasswordNotMatchException ex) {
        return buildResponseEntityForExceptions(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DtoToEntityConversionException.class})
    public ResponseEntity<Object> handleDtoToEntityConversionException(DtoToEntityConversionException ex) {
        return buildResponseEntityForExceptions(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RoleNotFoundException.class})
    public ResponseEntity<Object> handleRoleNotFoundException(RoleNotFoundException ex) {
        return buildResponseEntityForExceptions(ex, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = {ContactNotFoundException.class})
    public ResponseEntity<Object> handleContactsNotFoundException(UserNotFoundException ex) {
        return buildResponseEntityForExceptions(ex, HttpStatus.NOT_FOUND);
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