package com.course.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.model.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, String>{

	List<Course> findByCourseName(String courseName);
	
}
