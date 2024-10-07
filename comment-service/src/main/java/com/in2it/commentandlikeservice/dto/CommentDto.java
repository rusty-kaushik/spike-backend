package com.in2it.commentandlikeservice.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.v3.oas.annotations.Hidden;
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
	private String id;
	private String content;
	@JsonProperty(access = Access.READ_ONLY)
	private String blogId;

	private String userName;

	@Hidden
	private LocalDateTime createdDate;

	@JsonProperty(access = Access.WRITE_ONLY)
	private List<MultipartFile> media;

	@Hidden
	private List<String> mediaPath;

	@Hidden
	List<String> mediaData;

}
