package com.spike.SecureGate.feignClients;

import com.spike.SecureGate.DTO.courseDto.CourseCreationFeignDTO;
import com.spike.SecureGate.DTO.courseDto.CourseCreationRequestDTO;
import com.spike.SecureGate.DTO.courseDto.CourseUpdateRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "courseClient",  url = "${spike.service.course_service}")
public interface CourseFeignClient {

    @PostMapping("/course/save")
    ResponseEntity<Object> saveCourse(
        @RequestBody CourseCreationFeignDTO courseCreationFeignDTO
    );

    @GetMapping("/course/get-all")
    ResponseEntity<Object> getAllCourse();

    @PutMapping("/course/update/{id}")
    ResponseEntity<Object> updateCourse(
            @RequestBody CourseUpdateRequestDTO courseUpdateRequestDTO,
            @PathVariable String id
    );

    @GetMapping("/course/get-by-courseName/{courseName}")
    ResponseEntity<Object> getByCourseName(
            @PathVariable String courseName
    );

    @DeleteMapping("/course/delete-by-courseId/{id}")
    ResponseEntity<Object> deleteCourseById(
            @PathVariable String id
    );
}
