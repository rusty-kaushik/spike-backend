package com.in2it.blogservice.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@NotNull
	private long departmentId;
	
	@NotNull
	private long teamId;
	
	@NotNull
	private String authorId;
	
	@NotNull
	private String title;
	
	@NotNull
	@Column(length = 10000)
	private String content;
	@NotNull
	private String visiblity; 
	
	private long commentCount;
	private long likeCount;

	@Column(updatable = false)
	private String deletedBy;  // Basically   its RoleId whose delete the BLOG
	
	@Column(updatable = false)
	private String updatedBy;   // Basically   its RoleId whose update the BLOG
	
	

	 private String status;
	 
	 @Column(nullable = false, updatable = false)
	 private LocalDateTime cretedDateTime;
	 
	 private LocalDateTime updatedDateTime;
	 
	 private LocalDateTime deletedDateTime;
	 
	 private List<String> mediaFile;
	 
	 private List<String> mediaPath;
}
