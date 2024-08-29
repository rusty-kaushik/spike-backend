package com.in2it.blogmongo.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandler {
	
	
	@org.springframework.web.bind.annotation.ExceptionHandler(IncompliteInfoException.class)
	public ResponseEntity<?> requiredFieldException(IncompliteInfoException exception, WebRequest request){

		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), exception.getMessage(), request.getDescription(false));
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
		
	}
	
	
	@org.springframework.web.bind.annotation.ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<?> validationException(InvalidDataException exception, WebRequest request){
	
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), exception.getMessage(), request.getDescription(false));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		
		
		
	}
	
	
	@org.springframework.web.bind.annotation.ExceptionHandler(DataDoseNotExistException.class)
	public ResponseEntity<?> emptyDataException(DataDoseNotExistException exception, WebRequest request){
		
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), exception.getMessage(), request.getDescription(false));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		
		
	}
	
	
	@org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> unexpectedException(RuntimeException exception, WebRequest request){
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), exception.getMessage(), request.getDescription(false));
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
		
	}
	
	
	

}
