package com.in2it.commentService.payload;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(IdInvalidException.class)
	public ResponseEntity<?> idInvalidExceptionhandler(IdInvalidException ex, WebRequest request) {

		ExceptionStatus status = new ExceptionStatus(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity(status, HttpStatus.NOT_FOUND);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(InfoMissingException.class)
	public ResponseEntity<?> InfoMissingExceptionhandler(InfoMissingException ex, WebRequest request) {

		ExceptionStatus status = new ExceptionStatus(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity(status, HttpStatus.NOT_FOUND);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> UserNotFoundExceptionhandler(UserNotFoundException ex, WebRequest request) {

		ExceptionStatus status = new ExceptionStatus(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity(status, HttpStatus.NOT_FOUND);

	}
}
