package com.in2it.commentService.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.in2it.commentService.auditable.Auditable;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment extends Auditable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	@NotNull
	private String blogId;
	@NotNull
	private String authorId;
	@NotNull
	private String content;
	@NotNull
	private String status;
	
	private List<String> media;
	private List<String> mediaPath;
}
