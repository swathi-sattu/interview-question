package com.example.demo.course.exceptions;

public class InputValidationException extends Exception{
	
	private static final Integer STATUS_CODE = 400;
	
	private String message;
	
	public InputValidationException() {
		
	}
	
	public InputValidationException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	public Integer getStatusCode() {
		return STATUS_CODE;
	}
}
