package com.in2it.blogservice.reponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHandler<T>  {

//	public static  ResponseEntity<T> reponseHandler(Object data, HttpStatus statusCode, String message ){
//		Map<String, Object> map=new HashMap<>();
//        map.put("data", data);
//        map.put("httpStatus",statusCode );
//        map.put("massage", message);
//        map.put("timeStamp", LocalDateTime.now());
//		return ResponseEntity.status(HttpStatus.OK).body(map);
//		
//	}
	
	private T data;
	private String message;
	private HttpStatus httpStatus;
	private int status; 
	private LocalDateTime timeStamp;
	

}
