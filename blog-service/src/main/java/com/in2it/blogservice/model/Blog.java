package com.in2it.blogservice.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Blog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long departmentId;
	private long projectId;
	@NotNull
	private long authorId;
	
	@NotNull
	private String title;
	@NotNull
	private String content;
	@NotNull
	private String visiblity;
	private int commentCount;
	private int likeCount;
	
//	private List<String> media;
	private List<String> media;
	private List<String> mediaPath;
//	private List<String> mediaPath;
	private long updatedBy;
	
	

	private LocalDateTime cretedDateTime;
}
