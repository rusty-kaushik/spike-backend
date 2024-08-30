package com.in2it.commentService.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class CommentDto {
	
	private String id;
	private String blogId;
	private String authorId;
	private String content;
	private List<String> mediaName;
	private List<String> mediaPath;
	private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

}
