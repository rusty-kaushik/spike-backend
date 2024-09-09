package com.in2it.commentandlikeservice.exception;

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(InfoMissingException.class)
	public ResponseEntity<?> InfoMissingExceptionhandler(InfoMissingException ex, WebRequest request) {

		ExceptionStatus status = new ExceptionStatus(LocalDateTime.now(), HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(),
				HttpStatus.NON_AUTHORITATIVE_INFORMATION.getReasonPhrase(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity(status, HttpStatus.NOT_FOUND);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> UserNotFoundExceptionhandler(UserNotFoundException ex, WebRequest request) {

		ExceptionStatus status = new ExceptionStatus(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity(status, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(CommentNotFoundException.class)
	public ResponseEntity<?> commentNotFoundException(CommentNotFoundException exception, WebRequest request){
	
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage(), request.getDescription(false));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		
		
		
	}
	@ExceptionHandler(BlogNotFoundException.class)
	public ResponseEntity<?> blogNotFoundException (BlogNotFoundException exception, WebRequest request){
	
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage(), request.getDescription(false));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		
		
		
	}
	
	@ExceptionHandler(ServiceDownException.class)
	public ResponseEntity<?> serviceDownException (ServiceDownException exception, WebRequest request){
		
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), HttpStatus.BAD_GATEWAY.value(), HttpStatus.BAD_GATEWAY.getReasonPhrase(), exception.getMessage(), request.getDescription(false));
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
		
		
		
	}
}
