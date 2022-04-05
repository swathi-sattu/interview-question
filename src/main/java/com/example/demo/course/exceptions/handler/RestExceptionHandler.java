package com.example.demo.course.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.course.data.ExceptionResponseEntity;
import com.example.demo.course.exceptions.CourseNotFoundException;
import com.example.demo.course.exceptions.CourseRegistrationException;
import com.example.demo.course.exceptions.InputValidationException;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler (value = {InputValidationException.class})
	public ResponseEntity<ExceptionResponseEntity> handleInputValidationException(InputValidationException ex){
		ExceptionResponseEntity response = new ExceptionResponseEntity();
		response.setStatus(ex.getStatusCode());
		response.setMessage(ex.getMessage());
		return new ResponseEntity<ExceptionResponseEntity>(response, HttpStatus.valueOf(ex.getStatusCode()));
	}
	
	@ExceptionHandler (value = {CourseNotFoundException.class})
	public ResponseEntity<ExceptionResponseEntity> handleCourseNotFoundException(CourseNotFoundException ex){
		ExceptionResponseEntity response = new ExceptionResponseEntity();
		response.setStatus(ex.getStatusCode());
		response.setMessage(ex.getMessage());
		return new ResponseEntity<ExceptionResponseEntity>(response, HttpStatus.valueOf(ex.getStatusCode()));
	}
	
	@ExceptionHandler (value = {CourseRegistrationException.class})
	public ResponseEntity<ExceptionResponseEntity> handleCourseRegistrationException(CourseRegistrationException ex){
		ExceptionResponseEntity response = new ExceptionResponseEntity();
		response.setStatus(ex.getStatusCode());
		response.setMessage(ex.getMessage());
		return new ResponseEntity<ExceptionResponseEntity>(response, HttpStatus.valueOf(ex.getStatusCode()));
	}
}
