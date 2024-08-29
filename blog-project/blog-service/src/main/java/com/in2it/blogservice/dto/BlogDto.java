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
import lombok.Builder;
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
	
//	@NotNull
//	private long teamId;
	
	@NotNull
	private String authorId;    // Taking autherId as  userName
	
	@Size(max = 50,min = 2,message = "title must not be longer than 50 characters and not be less than 3 characters")
	@NotBlank(message = "title cannot be blank") 
	private String title;
	
	@NotBlank(message = "content cannot be blank")
	private String content;
	
//	@Hidden
//	private String visiblity;
	
	@Hidden
	private long commentCount;
	
	@Hidden
	private long likeCount;

	@Hidden
	private String deletedBy;
	
	 @Hidden
	 @JsonProperty(access = Access.WRITE_ONLY)
	 private String status;
	 
	 @Hidden
	 private LocalDateTime cretedDateTime;
	 @Hidden
	 private LocalDateTime updatedDateTime;

	 private String updatedBy;
	 
	 @JsonProperty(access = Access.WRITE_ONLY)
	 private List<MultipartFile> media;
	 
	 @Hidden
	 private List<String> mediaFile;


	 @Hidden
	 private List<String> mediaPath;
	
	 
	 
//	 @Hidden
//	 private LocalDateTime deletedDateTime;
	
}
