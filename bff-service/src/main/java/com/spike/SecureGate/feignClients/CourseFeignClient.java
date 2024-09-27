package com.spike.SecureGate.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "courseClient",  url = "${spike.service.course_service}")
public interface CourseFeignClient {

    @PostMapping("/save")
    ResponseEntity<Object> saveCourse(
           // @RequestBody CourseDto dto
    );

    @GetMapping("/get-all")
    ResponseEntity<Object> getAllCourse();

    @PutMapping("/update/{id}")
    ResponseEntity<Object> updateCourse(
          //  @RequestBody CourseUpdateDto dto,
            @PathVariable String id
    );

    @GetMapping("/get-by-courseName/{courseName}")
    ResponseEntity<Object> getByCourseName(
            @PathVariable String courseName
    );
}
