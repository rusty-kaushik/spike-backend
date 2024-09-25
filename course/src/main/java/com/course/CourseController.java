package com.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.dto.CourseDto;
import com.course.dto.CourseUpdateDto;
import com.course.response.Response;
import com.course.service.CourseService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@Operation(summary = "API to add a course")
	@PostMapping("/save")
	public ResponseEntity<Response<CourseDto>> saveCourse(@RequestBody CourseDto dto) {
		CourseDto course = courseService.saveCourse(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response<CourseDto>(course,
				"Course successfully created", HttpStatus.CREATED, HttpStatus.CREATED.value()));

	}
	
	@Operation(summary = "API to get all the course")
	@GetMapping("/get-all")
	public ResponseEntity<Response<List<CourseDto>>> getAllCourse() {
		List<CourseDto> course= courseService.getAllCourse();
		return ResponseEntity.status(HttpStatus.OK).body(new Response<List<CourseDto>>(course,
				"Get all the course", HttpStatus.OK, HttpStatus.OK.value()));
	}

	@Operation(summary = "API to update a course")
	@PutMapping("/update/{id}")
	public ResponseEntity<Response<CourseDto>> updateCourse(@RequestBody CourseUpdateDto dto, @PathVariable String id) {
		CourseDto course= courseService.updateCourse(dto, id);
		return ResponseEntity.status(HttpStatus.OK).body(new Response<CourseDto>(course,
				"Course successfully updated", HttpStatus.OK, HttpStatus.OK.value()));
	}

	@Operation(summary = "API to get all the course by course-name")
	@GetMapping("/get-by-courseName/{courseName}")
	public ResponseEntity<Response<List<CourseDto>>> getByCourseName(@PathVariable String courseName) {
		List<CourseDto> course = courseService.getByCourseName(courseName);
		return ResponseEntity.status(HttpStatus.OK).body(new Response<List<CourseDto>>(course,
				"Get all the course", HttpStatus.OK, HttpStatus.OK.value()));
	}

}
