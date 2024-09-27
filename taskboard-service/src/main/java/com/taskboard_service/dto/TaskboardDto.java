package com.taskboard_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskboardDto {

	@Hidden
	private UUID id;
	@NotNull
	private String userName;
	private long departmentId;
	private String title;
	@Column(name = "taskDescription")
	private String taskDes;

	@Hidden
	private Status status;

	 @JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime createdAt;

	 @JsonProperty(access = Access.READ_ONLY)
	private String createdBy;

	 @JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime lastModifiedDate;

	 @JsonProperty(access = Access.READ_ONLY)
	private String lastModifiedBy;
}
