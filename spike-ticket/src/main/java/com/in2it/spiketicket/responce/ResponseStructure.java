package com.in2it.spiketicket.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseStructure<T> {
	
	private T data;
	private String message;
	private int HttpStatus;

}
