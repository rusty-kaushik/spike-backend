package com.course.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String courseId;

	private String courseName;
	
	private String title;
	
	private String content;

	private String department;

	private String duration;

	private LocalDate createdAt;

	private String createdBy;
	
	private LocalDate updatedAt;

	private String updatedBy;

	private String deletedBy;

}
