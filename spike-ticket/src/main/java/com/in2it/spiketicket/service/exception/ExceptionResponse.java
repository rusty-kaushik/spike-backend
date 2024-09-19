package com.in2it.spiketicket.service.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExceptionResponse {

	private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
