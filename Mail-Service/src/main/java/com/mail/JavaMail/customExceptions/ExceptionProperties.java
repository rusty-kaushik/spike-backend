package com.mail.JavaMail.customExceptions;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ExceptionProperties {

	private LocalDateTime localDateTime;
	private String error;
	private int statusCode;
	private String message;
	private String path;
}
