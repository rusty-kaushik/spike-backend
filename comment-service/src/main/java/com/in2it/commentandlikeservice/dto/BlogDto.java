package com.in2it.commentandlikeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlogDto {
	
	private String id;
	
	private String title;
	
	private long commentCount;

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public long getCommentCount() {
//		return commentCount;
//	}
//
//	public void setCommentCount(long commentCount) {
//		this.commentCount = commentCount;
//	}
//
//	public BlogDto(String id, String title, long commentCount) {
//		super();
//		this.id = id;
//		this.title = title;
//		this.commentCount = commentCount;
//	}





}
