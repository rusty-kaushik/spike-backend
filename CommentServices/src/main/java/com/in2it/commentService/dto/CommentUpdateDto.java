package com.in2it.commentService.dto;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentUpdateDto 
{
	@NotNull
	private String id;
	@NotNull
	private String updatedBy;
	
	private Optional<String> content;
	private Optional<List<MultipartFile>> media;
}
