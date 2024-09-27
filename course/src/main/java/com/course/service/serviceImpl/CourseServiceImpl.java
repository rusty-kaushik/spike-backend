package com.course.service.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.dto.CourseDto;
import com.course.dto.CourseUpdateDto;
import com.course.exceptionHandler.CourseNotFoundException;
import com.course.exceptionHandler.IdInvalidException;
import com.course.model.Course;
import com.course.repo.CourseRepo;
import com.course.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CourseRepo courseRepo;

	public CourseDto saveCourse(CourseDto courseDto) {

		Course course = modelMapper.map(courseDto, Course.class);
		course.setCreatedAt(LocalDate.now());
		Course courseSave = courseRepo.save(course);
		CourseDto dto = modelMapper.map(courseSave, CourseDto.class);
		return dto;
	}

	public List<CourseDto> getAllCourse() {
		List<Course> courseList = courseRepo.findAll();
		List<CourseDto> courseDtoList = new ArrayList<>();

		for (Course course : courseList) {
			CourseDto dto = modelMapper.map(course, CourseDto.class);
			courseDtoList.add(dto);
		}

		return courseDtoList;
	}
	
	public List<CourseDto> getByCourseName(String courseName){
		
		List<Course> courseList=courseRepo.findByCourseName(courseName);
		List<CourseDto> courseDtoList = new ArrayList<>();

		for (Course course : courseList) {
			CourseDto dto = modelMapper.map(course, CourseDto.class);
			courseDtoList.add(dto);
		}
		return courseDtoList;
		
	}
	

	public CourseDto updateCourse(CourseUpdateDto dto, String courseId) {
		Course course = courseRepo.findById(courseId)
				.orElseThrow(() -> new IdInvalidException("Course dosen't exist with given id"));

		if (dto.getContent() != null)
			course.setContent(dto.getContent());

		course.setUpdatedAt(LocalDate.now());
		course.setUpdatedBy(course.getCreatedBy());
		course.setCourseName(dto.getCourseName());
		course.setTitle(dto.getTitle());
		course.setDepartment(dto.getDepartment());
		course.setDuration(dto.getDuration());
		Course saveCourse= courseRepo.save(course);
		CourseDto courseDto = modelMapper.map(saveCourse, CourseDto.class);
		
		return courseDto;
	}

	public void deleteCourse(String courseId) {
		Course course=courseRepo.findById(courseId).orElseThrow(()->new CourseNotFoundException("Course with given id doesn't exist "));
		courseRepo.deleteById(courseId);
		
	}
}
