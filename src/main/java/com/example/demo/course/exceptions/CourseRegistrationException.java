package com.example.demo.course.exceptions;

public class CourseRegistrationException extends Exception{
	
	private static final Integer STATUS_CODE = 400;
	
	private String message;
	
	public CourseRegistrationException() {
		
	}
	
	public CourseRegistrationException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	public Integer getStatusCode() {
		return STATUS_CODE;
	}
}
