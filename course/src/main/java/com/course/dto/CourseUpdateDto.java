package com.course.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
public class CourseUpdateDto {

	@JsonProperty(access = Access.READ_ONLY)
	private String courseId;

	private String courseName;

	private String title;

	private String content;

	private String department;

	private String duration;

}
