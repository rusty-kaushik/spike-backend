package com.in2it.blogmongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Media {
	
	@Id
	private String id;
	private String fileName;
	private String mediaType;

}
