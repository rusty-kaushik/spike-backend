package com.in2it.commentandlikeservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	private long id;
	private String content;
	@JsonIgnore
	private List<String> media;
	private long blogId;

	private String authorID;
	@JsonIgnore
	private List<MultipartFile> file;

	@Hidden
	private LocalDateTime createdDate;

	private List<String> mediaPath;

	
}
