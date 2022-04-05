package com.example.demo.course.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.demo.course.data.CancelParticipantEnrollmentRequest;
import com.example.demo.course.data.CourseRegistrationRequest;
import com.example.demo.course.data.CourseRequest;
import com.example.demo.course.exceptions.InputValidationException;

@RunWith(MockitoJUnitRunner.class)
public class ValidatorUtilTest {

	@InjectMocks
	ValidatorUtil validator;

	CourseRequest request;

	CourseRegistrationRequest registrationRequest = new CourseRegistrationRequest();

	CancelParticipantEnrollmentRequest cancelRequest = new CancelParticipantEnrollmentRequest();

	@Before
	public void setup() {
		request = new CourseRequest();
	}

	@Test(expected = InputValidationException.class)
	public void testValidateCourseId() throws Exception {
		validator.validateCourseId(Long.valueOf(1), Long.valueOf(0));
	}
	
	@Test(expected = InputValidationException.class)
	public void testValidateCourseRequest() throws Exception {
		validator.validateCourseRequest(request);
	}
	
	@Test(expected = InputValidationException.class)
	public void testValidateCourseRegistrationRequest() throws Exception {
		validator.validateCourseRegistrationRequest(registrationRequest);
	}
	
	@Test(expected = InputValidationException.class)
	public void testValidateCancelParticipantEnrollmentRequest() throws Exception {
		validator.validateCancelParticipantEnrollmentRequest(cancelRequest);
	}

}
