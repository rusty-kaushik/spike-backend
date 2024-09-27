package com.course.service;

import java.util.List;

import com.course.dto.CourseDto;
import com.course.dto.CourseUpdateDto;

public interface CourseService {

	public CourseDto saveCourse(CourseDto courseDto);
	
	public List<CourseDto> getAllCourse();
	
	public CourseDto updateCourse(CourseUpdateDto dto, String courseId);
	
	public List<CourseDto> getByCourseName(String courseName);
	
	public void deleteCourse(String courseId);
}
