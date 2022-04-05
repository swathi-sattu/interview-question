package com.example.demo.course.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.example.demo.course.data.CancelParticipantEnrollmentRequest;
import com.example.demo.course.data.CourseDetailsResponse;
import com.example.demo.course.data.CourseRegistrationRequest;
import com.example.demo.course.data.CourseRequest;
import com.example.demo.course.data.CourseResponse;
import com.example.demo.course.exceptions.InputValidationException;
import com.example.demo.course.service.CourseService;
import com.example.demo.course.util.ValidatorUtil;

@RunWith(MockitoJUnitRunner.class)
public class CourseControllerTest {

	@InjectMocks 
	CourseController controller;
	
	@Mock
	CourseService service;
	
	@Mock
	ValidatorUtil validator;
	
	CourseRequest request;
	
	CourseResponse response;
	
	CourseDetailsResponse detailsResponse;
	
	CourseRegistrationRequest registrationRequest = new CourseRegistrationRequest();
	
	CancelParticipantEnrollmentRequest cancelRequest = new CancelParticipantEnrollmentRequest();
	
	@Before
	public void setup() {
		request = new CourseRequest();
		request.setCapacity(10);
		request.setTitle("title");
		response = new CourseResponse();
		response.setId(Long.valueOf(1));
		detailsResponse = new CourseDetailsResponse();
		detailsResponse.setId(Long.valueOf(1));
	}
	
	@Test
	public void testCourseByTitle() throws Exception {
		Mockito.when(service.getCourseByTitle("title")).thenReturn(new ArrayList<CourseResponse>());
		 ResponseEntity<List<CourseResponse>> responseEntity = controller.getCourseByTitle("title");
		 assertNotNull(responseEntity);
	}
	
	@Test
	public void testCreateCourse() throws Exception {
		Mockito.when(service.createCourse(request)).thenReturn(response);
		 ResponseEntity<CourseResponse> responseEntity = controller.createCourse(request);
		 assertNotNull(responseEntity);
		 assertEquals(1, responseEntity.getBody().getId());
	}
	
	@Test(expected = InputValidationException.class)
	public void testCreateCourse_InputValidationException() throws Exception {
		Mockito.doThrow(new InputValidationException()).when(validator).validateCourseRequest(request);
		controller.createCourse(request);
	}
	
	
	@Test
	public void testCourseRegistration() throws Exception {
		Mockito.when(service.courseRegistration(1, registrationRequest)).thenReturn(detailsResponse);
		 ResponseEntity<CourseDetailsResponse> responseEntity = controller.courseRegistration(1,registrationRequest);
		 assertNotNull(responseEntity);
		 assertEquals(1, responseEntity.getBody().getId());
	}
	
	@Test
	public void testGetCourseDetails() throws Exception {
		Mockito.when(service.getCourseDetails(1)).thenReturn(detailsResponse);
		 ResponseEntity<CourseDetailsResponse> responseEntity = controller.getCourseDetails(1);
		 assertNotNull(responseEntity);
		 assertEquals(1, responseEntity.getBody().getId());
	}
	
	@Test
	public void testCancelEnrollment() throws Exception {
		Mockito.when(service.cancelEnrollment(1,cancelRequest)).thenReturn(detailsResponse);
		 ResponseEntity<CourseDetailsResponse> responseEntity = controller.cancelEnrollment(1, cancelRequest);
		 assertNotNull(responseEntity);
		 assertNull(responseEntity.getBody().getParticipantDetails());
	}
	
	@Test(expected = InputValidationException.class)
	public void testCancelEnrollment_InputValidationException() throws Exception {
		Mockito.doThrow(new InputValidationException()).when(validator).validateCancelParticipantEnrollmentRequest(cancelRequest);
		controller.cancelEnrollment(1, cancelRequest);
	}
}
