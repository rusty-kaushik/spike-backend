package com.in2it.blogservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BlogUpdateDto {
	
	@NotNull(message = "userName not null ")
	private String userName; 
	
	private String title;
	private String content;
	
	
	
}
