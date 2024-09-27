package com.spike.SecureGate.controllers;

import com.spike.SecureGate.DTO.courseDto.CourseCreationRequestDTO;
import com.spike.SecureGate.DTO.courseDto.CourseUpdateRequestDTO;
import com.spike.SecureGate.DTO.userDto.*;
import com.spike.SecureGate.JdbcHelper.UserDbService;
import com.spike.SecureGate.helper.DropdownHelper;
import com.spike.SecureGate.service.externalCourseService.CourseService;
import com.spike.SecureGate.service.externalUserService.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    // CREATE A NEW USER
    @Operation(
            summary = "Admin creates a new course",
            description = "Creates a new course ."
    )
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createCourse(
            @RequestBody CourseCreationRequestDTO courseCreationRequestDTO
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        String userName = authentication.getName();
        logger.info("Started creating new course");
        ResponseEntity<Object> course = courseService.createCourse(userName,courseCreationRequestDTO);
        logger.info("Finished creating new course");
        return course;
    }

    // GET ALL COURSES
    @Operation(
            summary = "User gets all course",
            description = "User gets all course."
    )
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> getAllCourses() {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching all course");
        ResponseEntity<Object> course = courseService.getAllCourses();
        logger.info("Finished fetching all course");
        return course;
    }

    // GET BY COURSE NAME
    @Operation(
            summary = "User gets course by name ",
            description = "User gets course by name."
    )
    @GetMapping("/{courseName}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','MANAGER')")
    public ResponseEntity<Object> getCourseByName(
            @PathVariable String courseName
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching all course");
        ResponseEntity<Object> course = courseService.getCourseByName(courseName);
        logger.info("Finished fetching all course");
        return course;
    }

    // UPDATE COURSE
    @Operation(
            summary = "Admin updates a Course",
            description = "User gets course by name."
    )
    @PutMapping("/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> updateCourse(
            @RequestBody CourseUpdateRequestDTO courseUpdateRequestDTO,
            @PathVariable String courseId
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching all course");
        ResponseEntity<Object> course = courseService.updateCourse(courseUpdateRequestDTO,courseId);
        logger.info("Finished fetching all course");
        return course;
    }

    // UPDATE COURSE
    @Operation(
            summary = "Admin updates a Course",
            description = "User gets course by name."
    )
    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteCourse(
            @PathVariable String courseId
    ) {
        logger.info("Started authenticating");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication Successful");
        logger.info("Started fetching all course");
        ResponseEntity<Object> course = courseService.deleteCourse(courseId);
        logger.info("Finished fetching all course");
        return course;
    }
}
