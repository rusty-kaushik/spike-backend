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

}
