package com.mail.JavaMail.customExceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionClass {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(MailNotSentException.class)
	public  ResponseEntity<?> mailNotSentException(MailNotSentException ex ,WebRequest request)
	{

		ExceptionProperties exceptionResponse=new ExceptionProperties(LocalDateTime.now(), HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));		
		
		return new ResponseEntity(exceptionResponse,HttpStatus.NOT_FOUND);

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(DirectoryException.class)
	public  ResponseEntity<?> mailNotSentException(DirectoryException ex ,WebRequest request)
	{

		ExceptionProperties exceptionResponse=new ExceptionProperties(LocalDateTime.now(), HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));		
		
		return new ResponseEntity(exceptionResponse,HttpStatus.NOT_FOUND);

	}
}
