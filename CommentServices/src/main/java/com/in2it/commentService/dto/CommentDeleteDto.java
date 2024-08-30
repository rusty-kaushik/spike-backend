package com.in2it.commentService.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class CommentDeleteDto {
	private String id;
	private String deletedBy;
	
}
