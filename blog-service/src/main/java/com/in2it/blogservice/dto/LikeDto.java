package com.in2it.blogservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor

@Data
public class LikeDto {
	
	private long id;
	private long blogId;
	private long authorId;

}
