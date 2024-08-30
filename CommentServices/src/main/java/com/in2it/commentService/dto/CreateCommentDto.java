package com.in2it.commentService.dto;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class CreateCommentDto {

	@NotNull
	private String blogId;
	@NotNull
	private String authorId;
	@NotNull
	private String content;
	private List<MultipartFile> media;
	
}
