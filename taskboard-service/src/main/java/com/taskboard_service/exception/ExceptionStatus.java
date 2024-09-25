package com.taskboard_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionStatus {


	private int httpStatusCode;  
	private String error;    
	private String message;    

	
}
