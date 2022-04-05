package com.example.demo.course.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.demo.course.data.CancelParticipantEnrollmentRequest;
import com.example.demo.course.data.CourseDetailsResponse;
import com.example.demo.course.data.CourseRegistrationRequest;
import com.example.demo.course.data.CourseRequest;
import com.example.demo.course.data.CourseResponse;
import com.example.demo.course.exceptions.CourseNotFoundException;
import com.example.demo.course.exceptions.InputValidationException;
import com.example.demo.course.persistence.CourseEntity;
import com.example.demo.course.persistence.CourseRepository;
import com.example.demo.course.persistence.ParticipantEntity;
import com.example.demo.course.persistence.ParticipantsRepository;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTest {

	@InjectMocks
	CourseService service;

	@Mock
	CourseRepository repository;

	@Mock
	ParticipantsRepository participantRepository;

	CourseRequest request;

	CourseResponse response;

	CourseDetailsResponse detailsResponse;

	CourseRegistrationRequest registrationRequest = new CourseRegistrationRequest();

	CancelParticipantEnrollmentRequest cancelRequest = new CancelParticipantEnrollmentRequest();

	CourseEntity courseEntity = new CourseEntity();
	Optional<CourseEntity> courseEnt = null;

	@Before
	public void setup() {
		request = new CourseRequest();
		request.setCapacity(10);
		request.setTitle("title");
		request.setStartDate(LocalDate.now());
		cancelRequest.setCancelDate(getTodayMinusdays(5));
		registrationRequest.setRegistrationDate(getTodayMinusdays(5));
		response = new CourseResponse();
		response.setId(Long.valueOf(1));
		detailsResponse = new CourseDetailsResponse();
		detailsResponse.setId(Long.valueOf(1));
		courseEntity.setId(Long.valueOf(1));
		courseEntity.setRemaining(1);
		courseEntity.setStartDate( LocalDate.now());
		courseEntity.setEndDate( LocalDate.now());
		courseEnt = Optional.of(courseEntity);
		Mockito.when(repository.findById((Long.valueOf(1)))).thenReturn(courseEnt);
	}

	@Test
	public void testCourseByTitle() throws Exception {
		Mockito.when(repository.findByTitle("title")).thenReturn(new ArrayList<CourseEntity>());
		List<CourseResponse> responseEntity = service.getCourseByTitle("title");
		assertNotNull(responseEntity);
	}

	@Test
	public void testCreateCourse() throws Exception {
		Mockito.when(repository.save(Mockito.any())).thenReturn(courseEntity);
		CourseResponse responseEntity = service.createCourse(request);
		assertNotNull(responseEntity);
		assertEquals(1, responseEntity.getId());
	}

	@Test
	public void testCourseRegistration() throws Exception {
		
		Mockito.when(participantRepository.exists(Mockito.any())).thenReturn(false);
		CourseDetailsResponse responseEntity = service.courseRegistration(1, registrationRequest);
		assertNotNull(responseEntity);
		assertEquals(1, responseEntity.getId());
	}
	
	@Test(expected = CourseNotFoundException.class)
	public void testCourseRegistration_CourseNotFound() throws Exception {
		Mockito.when(repository.findById((Long.valueOf(1)))).thenReturn(Optional.empty());
		service.courseRegistration(1, registrationRequest);
	}

	@Test
	public void testGetCourseDetails() throws Exception {
		CourseDetailsResponse responseEntity = service.getCourseDetails(1);
		assertNotNull(responseEntity);
		assertEquals(1, responseEntity.getId());
	}

	@Test
	public void testCancelEnrollment() throws Exception {
		Mockito.when(participantRepository.findByName(Long.valueOf(1), cancelRequest.getName()))
				.thenReturn(new ParticipantEntity());
		CourseDetailsResponse responseEntity = service.cancelEnrollment(1, cancelRequest);
		assertNotNull(responseEntity);
		assertNotNull(responseEntity.getParticipantDetails());
	}
	
	@Test(expected = InputValidationException.class)
	public void testCancelEnrollment_InvalidDate() throws Exception {
		cancelRequest.setCancelDate(LocalDate.now());
		service.cancelEnrollment(1, cancelRequest);
	}
	
	private LocalDate getTodayMinusdays(int days) {
		return LocalDate.now().minusDays(5);
	}

}
