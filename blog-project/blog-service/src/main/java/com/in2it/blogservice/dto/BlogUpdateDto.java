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

//	@NotNull
//	private UUID id;
	
	@NotNull
	private String authorId; 
	private String title;
//	@NotBlank(message = "content cannot be blank")
	private String content;
	
	
//	private String visiblity;
	
//	@JsonProperty(access = Access.WRITE_ONLY)
//	private List<MultipartFile> media;
	
}
