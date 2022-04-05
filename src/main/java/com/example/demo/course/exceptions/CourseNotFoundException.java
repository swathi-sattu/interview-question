package com.example.demo.course.exceptions;

public class CourseNotFoundException extends Exception{
	
	private static final Integer STATUS_CODE = 404;
	
	private String message;
	
	public CourseNotFoundException() {
		
	}
	
	public CourseNotFoundException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	public Integer getStatusCode() {
		return STATUS_CODE;
	}
}
