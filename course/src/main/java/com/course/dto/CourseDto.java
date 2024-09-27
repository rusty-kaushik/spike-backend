package com.course.dto;

import java.time.LocalTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CourseDto {

	@JsonProperty(access = Access.READ_ONLY)
	private String courseId;

	@Nonnull
	private String courseName;

	@NotNull
	private String content;

	@NotNull
	private String department;

	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date startDate;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date endDate;

	private String createdBy;
	@JsonProperty(access = Access.READ_ONLY)
	private String updatedBy;

}
