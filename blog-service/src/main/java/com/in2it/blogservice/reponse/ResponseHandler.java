package com.in2it.blogservice.reponse;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResponseHandler<T>  {
	private T data;
	private String message;
	private HttpStatus httpStatus;
	private int statusCode; 
	

  public	ResponseHandler(T data, String message, HttpStatus httpStatus , int statusCode){
		
		this.data=data;
		this.message=message;
		this.httpStatus=httpStatus;
		this.statusCode=statusCode;
	
	}

}


