package com.in2it.commentandlikeservice.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CommentDto {

	@Hidden
	private UUID id;
	private String content;
	@JsonIgnore
	private long blogId;

	private String userName;
	

	@Hidden
	private LocalDateTime createdDate;

	@Size(max = 1_000_000) // 1 MB
	 @JsonProperty(access = Access.WRITE_ONLY)
	private List<MultipartFile> media;
	
	 @Hidden
	 private List<String> mediaPath;
	

	
}
