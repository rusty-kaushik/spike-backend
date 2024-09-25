package com.in2it.blogservice.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class BlogDto {

	@Hidden
	private UUID id;

	@NotBlank(message = "departmentId cannot be blank")
	@JsonProperty(access = Access.WRITE_ONLY)
	private long departmentId;
	

	@JsonProperty(access = Access.READ_ONLY)
	private String departmentName;

	@NotNull
	@Column(name="userName")
	private String name; // Taking autherId as userName
	
	@NotNull
	private long userId; // Taking autherId as userName

	@Size(max = 50, min = 2, message = "title must not be longer than 50 characters and not be less than 3 characters")
	@NotBlank(message = "title cannot be blank")
	private String title;

	@NotBlank(message = "content cannot be blank")
	private String content;

	@Hidden
	private long commentCount;

	@Hidden
	private long likeCount;

	@Hidden
	private boolean status;

	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime createdDateTime;

	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime updatedDateTime;

	@JsonProperty(access = Access.READ_ONLY)
	private String updatedBy;

	@JsonProperty(access = Access.READ_ONLY)
	private List<String> mediaFile;

	
    @JsonProperty(access = Access.READ_ONLY)
	private String profilePic;

}
