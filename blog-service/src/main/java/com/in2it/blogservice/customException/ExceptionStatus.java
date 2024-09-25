package com.in2it.blogservice.customException;

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
	/*
	 * private LocalDateTime timestamp;
	 *  private String path;
	 */
	
	
}
