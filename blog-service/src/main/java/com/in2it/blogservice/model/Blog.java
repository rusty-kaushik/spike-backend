package com.in2it.blogservice.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Blog {
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	
	@NotNull
	private long departmentId;
	
	
	@NotNull
	private String userId;     //  AUTHOR ID THAT MEANS WHOSE POSTED BLOG FOR PARTICULLER DEPARTMENT 
	
	@NotNull
	private String title;
	
	@NotNull
	@Column(length = 10000)
	private String content;
	

	
	private long commentCount;
	
	private long likeCount;

	

	private String updatedBy;   // Basically   its RoleId whose update the BLOG
	
	

	 private String status;
	 
	 @Column(nullable = false, updatable = false)
	 private LocalDateTime cretedDateTime;
	 
	 private LocalDateTime updatedDateTime;
	 
	 
	 private List<String> mediaFile;
	 
	 private List<String> mediaPath;
}
