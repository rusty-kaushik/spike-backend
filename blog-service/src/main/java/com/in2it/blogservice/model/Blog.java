package com.in2it.blogservice.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
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
	
	
	@Digits(integer = 5 , fraction = 0, message = "min length of digits is 5")
	@Range(min = 1 , message = "min number of digits is should be 1")
	private long departmentId;
	
	
	@NotBlank(message = "userName cannot be blank") 
	private String userName;     //  AUTHOR ID THAT MEANS WHOSE POSTED BLOG FOR PARTICULLER DEPARTMENT 
	
	@NotBlank(message = "title cannot be blank") 
	private String title;
	
	@NotBlank(message = "content cannot be blank")
	@Column(length = 10000)
	private String content;
	

	
	private long commentCount;
	
	private long likeCount;

	

	private String updatedBy; // Basically its RoleId whose update the BLOG
	
	

	 private boolean status;
	 
	 @Column(nullable = false, updatable = false)
	 private LocalDateTime createdDateTime;
	 
	 private LocalDateTime updatedDateTime;
	 
	 
	 private List<String> mediaFile;
	 
	 private List<String> mediaPath;
}
