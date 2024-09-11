package com.in2it.commentandlikeservice.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

	private T data;
	private String message;
	private HttpStatus httpStatus;
	private int statusCode; 
	private LocalDateTime timeStamp;
}
