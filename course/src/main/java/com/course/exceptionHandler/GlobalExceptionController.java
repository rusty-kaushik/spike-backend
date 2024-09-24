package com.course.exceptionHandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionController {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(IdInvalidException.class)
	public ResponseEntity<?> idInvalidExceptionhandler(IdInvalidException ex, WebRequest request) {

		ExceptionResponse status = new ExceptionResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity(status, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(CourseNotFoundException.class)
	public ResponseEntity<?> courseNotFoundException(CourseNotFoundException ex, WebRequest request){
		ExceptionResponse status= new ExceptionResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?>  generalExceptionHandler(Exception ex, WebRequest request){
		ExceptionResponse status= new ExceptionResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
	}
}
