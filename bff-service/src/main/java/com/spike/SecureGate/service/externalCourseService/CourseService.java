package com.spike.SecureGate.service.externalCourseService;

import com.spike.SecureGate.DTO.courseDto.CourseCreationRequestDTO;
import com.spike.SecureGate.DTO.courseDto.CourseUpdateRequestDTO;
import org.springframework.http.ResponseEntity;

public interface CourseService {


    ResponseEntity<Object> createCourse(String userName, CourseCreationRequestDTO courseCreationRequestDTO);

    ResponseEntity<Object> getAllCourses();

    ResponseEntity<Object> getCourseByName(String courseName);

    ResponseEntity<Object> updateCourse(CourseUpdateRequestDTO courseUpdateRequestDTO, String courseId);

    ResponseEntity<Object> deleteCourse(String courseId);
}
