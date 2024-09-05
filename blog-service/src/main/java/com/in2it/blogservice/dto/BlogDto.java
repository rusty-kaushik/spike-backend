package com.in2it.blogservice.dto;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class BlogDto {
	

	@Hidden
	private UUID id;
	
	@NotNull
	private long departmentId;
	

	@NotNull
	private String userId;    // Taking autherId as  userName
	
	@Size(max = 50,min = 2,message = "title must not be longer than 50 characters and not be less than 3 characters")
	@NotBlank(message = "title cannot be blank") 
	private String title;
	
	@NotBlank(message = "content cannot be blank")
	private String content;
	

	
	 @JsonProperty(access = Access.READ_ONLY)
	private long commentCount;
	
	 @JsonProperty(access = Access.READ_ONLY)
	private long likeCount;

	 @JsonProperty(access = Access.READ_ONLY)
	 private String status;
	 
	 @JsonProperty(access = Access.READ_ONLY)
	 private LocalDateTime cretedDateTime;
	 @JsonProperty(access = Access.READ_ONLY)
	 private LocalDateTime updatedDateTime;

	 @JsonProperty(access = Access.READ_ONLY)
	 private String updatedBy;
	 
	 @JsonProperty(access = Access.WRITE_ONLY)
	 private List<MultipartFile> media;
	 

	 @JsonProperty(access = Access.READ_ONLY)
	 private List<String> mediaFile;


//	 @Hidden
//	 private List<String> mediaPath;
	
	 

	
}
