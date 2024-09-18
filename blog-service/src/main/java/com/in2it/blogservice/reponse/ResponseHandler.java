package com.in2it.blogservice.reponse;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
public class ResponseHandler<T>  {
	private T data;
	private String message;
	private HttpStatus httpStatus;
	private int status; 
	private int totalResults;
	private int pageNo;
	private int pageSize;
	private LocalDateTime timeStamp;
	

	ResponseHandler(){
		
	}
  public	ResponseHandler(T data, String message, HttpStatus httpStatus , int status, LocalDateTime timeStamp){
		
		this.data=data;
		this.message=message;
		this.httpStatus=httpStatus;
		this.status=status;
		this.timeStamp=timeStamp;
	}
	
  public	ResponseHandler(T data, String message, HttpStatus httpStatus , int status, int totalResults,int pageNo,int pageSize ,LocalDateTime timeStamp){
		this.data=data;
		this.message=message;
		this.httpStatus=httpStatus;
		this.status=status;
		this.totalResults=totalResults;
		this.pageNo=pageNo;
		this.pageSize=pageSize;
		this.timeStamp=timeStamp;
	}
}


