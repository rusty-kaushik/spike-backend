package com.in2it.blogservice.reponse;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseHandlerWithPageable<T> {

	private T data;
	private String message;
	private HttpStatus httpStatus;
	private int statusCode; 
	private int totalResults;
	private int pageNo;
	private int pageSize;

	
}
