package com.spike.SecureGate.service.externalCourseService;

import com.spike.SecureGate.DTO.courseDto.CourseCreationFeignDTO;
import com.spike.SecureGate.DTO.courseDto.CourseCreationRequestDTO;
import com.spike.SecureGate.DTO.courseDto.CourseUpdateRequestDTO;
import com.spike.SecureGate.exceptions.UnexpectedException;
import com.spike.SecureGate.feignClients.CourseFeignClient;
import com.spike.SecureGate.helper.CourseHelper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private CourseFeignClient courseFeignClient;

    @Autowired
    private CourseHelper courseHelper;

    @Override
    public ResponseEntity<Object> createCourse(String userName, CourseCreationRequestDTO courseCreationRequestDTO) {
        try{
            CourseCreationFeignDTO courseCreationFeignDTO = courseHelper.courseCreationDtoTOFeignDto(courseCreationRequestDTO, userName);
            return courseFeignClient.saveCourse(courseCreationFeignDTO);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while creating a Course", e);
            throw new UnexpectedException("UnexpectedException","An unexpected error occurred while creating a course: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getAllCourses() {
        try{
            return courseFeignClient.getAllCourse();
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while creating a Course", e);
            throw new UnexpectedException("UnexpectedException","An unexpected error occurred while creating a course: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getCourseByName(String courseName) {
        try{
            return courseFeignClient.getByCourseName(courseName);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while creating a Course", e);
            throw new UnexpectedException("UnexpectedException","An unexpected error occurred while creating a course: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> updateCourse(CourseUpdateRequestDTO courseUpdateRequestDTO, String courseId) {
        try{
            return courseFeignClient.updateCourse(courseUpdateRequestDTO, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while creating a Course", e);
            throw new UnexpectedException("UnexpectedException","An unexpected error occurred while creating a course: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> deleteCourse(String courseId) {
        try{
            return courseFeignClient.deleteCourseById(courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        } catch (Exception e) {
            logger.error("Error occurred while creating a Course", e);
            throw new UnexpectedException("UnexpectedException","An unexpected error occurred while creating a course: " + e.getMessage());
        }
    }
}
