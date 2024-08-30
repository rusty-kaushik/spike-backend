package com.in2it.blogservice.dto;


import java.util.List;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BlogDto {
	
	@NotNull
	@Hidden
	private long id;
	@NotNull
	private long departmentId;
	@NotNull
	private long projectId;
	
	@Size(max = 50,min = 2,message = "title must not be longer than 50 characters and not be less than 3 characters")
	@NotBlank(message = "title cannot be blank") 
	private String title;
	@NotBlank(message = "content cannot be blank")
	private String content;
	private String visiblity;
	@Hidden	
	private int commentCount;
	@Hidden
	private int likeCount;
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<MultipartFile> media;
//	private MultipartFile media;
	@Hidden
	private List<String> imgPath;
	@NotNull
	private long authorId;
//	private LocalDateTime cretedDateTime;
//	private byte[] img;
	
	
}
