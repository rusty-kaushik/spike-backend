package com.in2it.commentandlikeservice.feign;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogResponce<T> {
	
	private T data;
	private String message;
	private HttpStatus httpStatus;
	private int status;
	private LocalDateTime timeStamp;

}
