package com.example.demo.course.util;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.example.demo.course.data.CancelParticipantEnrollmentRequest;
import com.example.demo.course.data.CourseRegistrationRequest;
import com.example.demo.course.data.CourseRequest;
import com.example.demo.course.exceptions.InputValidationException;

@Component
public class ValidatorUtil {
	
	public void validateCourseId(long id, Long courseId) throws InputValidationException {
		if (id != courseId)
			throw new InputValidationException("CourseIds doesnt match");
		
	}
	
	public void validateCourseRequest(CourseRequest request) throws InputValidationException {
		if(request.getTitle() == null || request.getTitle().length()==0)
			throw new InputValidationException("Title has invalid value");
		else if(request.getCapacity() <= 0)
			throw new InputValidationException("Capacity has invalid value.It should be > 0");
		else if(request.getStartDate() !=null) {
			validateDate(request.getStartDate(),"StartDate");	
		}else if(request.getEndDate() !=null) {
			validateDate(request.getEndDate(),"EndDate");	
		}
		
			
	}
	
	public void validateCourseRegistrationRequest(CourseRegistrationRequest request) throws InputValidationException {
		if(request.getName() == null || request.getName().length()==0)
			throw new InputValidationException("Name has invalid value");
		else if(request.getCourseId() <= 0)
			throw new InputValidationException("CourseId has invalid value.It should be > 0");
		else if(request.getRegistrationDate() !=null) {
			validateDate(request.getRegistrationDate(),"RegistrationDate");	
		}
		
			
	}
	
	public void validateCancelParticipantEnrollmentRequest(CancelParticipantEnrollmentRequest request) throws InputValidationException {
		if(request.getName() == null || request.getName().length()==0)
			throw new InputValidationException("Name has invalid value");
		else if(request.getCourseId() <= 0)
			throw new InputValidationException("CourseId has invalid value.It should be > 0");
		else if(request.getCancelDate() !=null) {
			validateDate(request.getCancelDate(),"CancelDate");	
		}
		
			
	}
	public void validateDate(LocalDate date, String fieldName) throws InputValidationException {
		try {
			LocalDate.parse(date.toString());
		}catch (DateTimeException e) {
			throw new InputValidationException(fieldName + " has invalid value.It should be > 0");
		}
	}
}
