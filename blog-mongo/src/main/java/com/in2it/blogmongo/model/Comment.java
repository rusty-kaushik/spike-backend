package com.in2it.blogmongo.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Document(collection = "comments")
public class Comment {
	
	@Id
	private String id;
	private String content;
	private String media;
	private String blogId;
	private String authorid;
	private LocalDateTime createdAt;
	private int likesCount;
	private List<Like> likes;
	private List<Comment> replies;
	private String status;
	private String deletedById;
	private LocalDateTime deletedAt;

}
