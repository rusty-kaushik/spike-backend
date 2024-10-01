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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
	
	

	private String departmentName;
	
	 
	@Column(name="userName")
	@NotBlank(message = "Invalid name: Empty name")
    @NotNull(message = "Invalid name: Name is NULL")
	private String name;;     //  AUTHOR ID THAT MEANS WHOSE POSTED BLOG FOR PARTICULLER DEPARTMENT 
	
	
	private long userId; // Taking autherId as userName
	

	@NotBlank(message = "Invalid title: Empty title")
    @NotNull(message = "Invalid title: content is NULL")
    @Size(min = 3, max = 200, message = "Invalid title: Must be of 3 - 200 characters")
	private String title;
	
	@NotBlank(message = "Invalid content: Empty content")
    @NotNull(message = "Invalid content: content is NULL")
    @Size(min = 3, max = 3000, message = "Invalid content: Must be of 3 - 3000 characters")
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
