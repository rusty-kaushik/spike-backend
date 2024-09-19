package com.taskboard_service.dto;

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
public class TaskboardUpdateDto {


	@NotNull
	private String userName;
	
	private String title;
	@Column(name = "taskDescription")
	private String taskDes;
}
