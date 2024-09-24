package com.course.exceptionHandler;

public class CourseNotFoundException extends RuntimeException {

	public CourseNotFoundException(String msg) {
		super(msg);
	}

}
