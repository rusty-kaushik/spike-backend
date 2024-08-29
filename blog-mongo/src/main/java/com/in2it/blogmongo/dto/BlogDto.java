package com.in2it.blogmongo.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.in2it.blogmongo.model.Comment;
import com.in2it.blogmongo.model.Like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BlogDto {
	
	private String id;
	private String title;
	private String content;
	private String visiblity;
	private Long departmentId;
	private Long teamId;
	private List<String> media;
	private int commentsCount;
	private int likesCount;
	private List<Like> likes;
	private List<Comment> comments;
	private List<String> tags;
	private String authorId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String status;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long deletedById;
	@JsonProperty(access = Access.WRITE_ONLY)
	private LocalDateTime deletedAt;
	
	

}
