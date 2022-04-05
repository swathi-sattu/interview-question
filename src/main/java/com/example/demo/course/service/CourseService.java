package com.example.demo.course.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.demo.course.data.CancelParticipantEnrollmentRequest;
import com.example.demo.course.data.CourseDetailsResponse;
import com.example.demo.course.data.CourseRegistrationRequest;
import com.example.demo.course.data.CourseRequest;
import com.example.demo.course.data.CourseResponse;
import com.example.demo.course.data.ParticipantDetails;
import com.example.demo.course.exceptions.CourseNotFoundException;
import com.example.demo.course.exceptions.CourseRegistrationException;
import com.example.demo.course.exceptions.InputValidationException;
import com.example.demo.course.persistence.CourseEntity;
import com.example.demo.course.persistence.CourseRepository;
import com.example.demo.course.persistence.ParticipantEntity;
import com.example.demo.course.persistence.ParticipantsRepository;

@Service
public class CourseService {
	@Autowired
	CourseRepository repository;

	@Autowired
	ParticipantsRepository participantRepository;

	/**
	 * Creates a new Course
	 * 
	 * @param request
	 * @return
	 * @throws RecordNotFoundException
	 */
	public CourseResponse createCourse(CourseRequest request) {
		CourseEntity entity = createInput(request);
		CourseResponse response = new CourseResponse();
		entity = repository.save(entity);
		BeanUtils.copyProperties(entity, response);
		return response;

	}

	private CourseEntity createInput(CourseRequest request) {
		CourseEntity entity = new CourseEntity();
		entity.setCapacity(request.getCapacity());
		entity.setTitle(request.getTitle());
		entity.setEndDate(request.getEndDate());
		entity.setStartDate(request.getStartDate());
		entity.setRemaining(request.getCapacity());
		return entity;
	}

	/**
	 * Gets the courses by title
	 * 
	 * @param title
	 * @return CourseResponse - List of courses
	 */
	public List<CourseResponse> getCourseByTitle(String title) {
		List<CourseEntity> coursesList = repository.findByTitle(title);
		List<CourseResponse> coursesResponse = new ArrayList<>();

		coursesList.stream().forEach((course) -> {
			CourseResponse courseResponse = new CourseResponse();
			courseResponse.setId(course.getId());
			courseResponse.setTitle(course.getTitle());
			courseResponse.setStartDate(course.getStartDate());
			courseResponse.setEndDate(course.getEndDate());
			courseResponse.setCapacity(course.getCapacity());
			courseResponse.setRemaining(course.getRemaining());
			coursesResponse.add(courseResponse);
		});
		return coursesResponse;
	}

	/**
	 * Gets the course details along with participants for the given course id
	 * 
	 * @param id
	 * @return CourseDetailsResponse
	 */
	public CourseDetailsResponse getCourseDetails(long id) {
		CourseDetailsResponse courseDetailsResponse = null;
		Optional<CourseEntity> course = repository.findById(id);
		if (course.isPresent()) {
			courseDetailsResponse = populateParticipantsDetails(id, course.get());
		}
		return courseDetailsResponse;
	}

	/**
	 * Populates the participants details for the given course id and return the
	 * course details
	 */
	private CourseDetailsResponse populateParticipantsDetails(long id, CourseEntity course) {
		CourseDetailsResponse courseDetailsResponse = new CourseDetailsResponse();
		List<ParticipantEntity> participants = participantRepository.findByCourseId(id);
		List<ParticipantDetails> participantDetails = new ArrayList<>();

		participants.stream().forEach((participantEntity) -> {
			ParticipantDetails participant = new ParticipantDetails();
			participant.setName(participantEntity.getName());
			participant.setRegistrationDate(participantEntity.getRegistrationDate());
			participantDetails.add(participant);
		});
		BeanUtils.copyProperties(course, courseDetailsResponse);
		courseDetailsResponse.setParticipantDetails(participantDetails);
		return courseDetailsResponse;
	}
	
	/**
	 * Registers the participant for a given course id
	 * @param id
	 * @param registrationRequest
	 * @return CourseDetailsResponse
	 * @throws CourseNotFoundException 
	 * @throws CourseRegistrationException 
	 * @throws InputValidationException 
	 * @throws RecordNotFoundException
	 */
	public CourseDetailsResponse courseRegistration(long id, CourseRegistrationRequest registrationRequest) throws CourseNotFoundException, CourseRegistrationException, InputValidationException
	{
		ParticipantEntity participantEntity = new ParticipantEntity();
		CourseEntity courseEntity = null;
		CourseDetailsResponse courseDetailsResponse = null;
		Optional<CourseEntity> course = repository.findById(id);
		if (!course.isPresent())
			throw new CourseNotFoundException("Course doesnt exist");
		else if (Integer.valueOf(course.get().getRemaining()) == 0)
			throw new CourseRegistrationException("Course is Full");
		else if (isAlreadyEnrolledForCourse(id, registrationRequest.getName()))
			throw new CourseRegistrationException(registrationRequest.getName() + " already enrolled in course");
		else if (!isDateValid(course.get().getStartDate(), registrationRequest.getRegistrationDate()))
			throw new InputValidationException("Registration date is invalid");
		else {
			BeanUtils.copyProperties(registrationRequest, participantEntity);
			participantEntity = participantRepository.save(participantEntity);
			// reduce remaining by 1 after successful registration for a Course
			courseEntity = course.get();
			courseEntity.setRemaining(courseEntity.getRemaining() - 1);
			repository.save(courseEntity);
			courseDetailsResponse = populateParticipantsDetails(id, course.get());
		}

		return courseDetailsResponse;
	}
	
	/**
	 * Cancels the participants enrollment for the given course id
	 * @param id
	 * @param registrationRequest
	 * @return CourseDetailsResponse
	 * @throws CourseNotFoundException 
	 * @throws CourseRegistrationException 
	 * @throws InputValidationException 
	 * @throws RecordNotFoundException
	 */
	public CourseDetailsResponse cancelEnrollment(long id, CancelParticipantEnrollmentRequest cancelRequest) throws CourseNotFoundException, InputValidationException
	{
		CourseDetailsResponse courseDetailsResponse = null;
		CourseEntity courseEntity = null;
		ParticipantEntity participantEntity = participantRepository.findByName(id, cancelRequest.getName());
		Optional<CourseEntity> course = repository.findById(id);
		if (!course.isPresent())
			throw new CourseNotFoundException("Course doesnt exist");
		else if (!isDateValid(course.get().getStartDate(), cancelRequest.getCancelDate()))
			throw new InputValidationException("Cancel date is invalid");
		else {
			BeanUtils.copyProperties(cancelRequest, participantEntity);
			participantRepository.delete(participantEntity);
			// increment remaining by 1 after successful registration for a Course
			courseEntity = course.get();
			courseEntity.setRemaining(courseEntity.getRemaining() + 1);
			repository.save(courseEntity);
			courseDetailsResponse = populateParticipantsDetails(id, course.get());
		}

		return courseDetailsResponse;
	}

	/**
	 * Checks if registration/cancel date falls in last 3 days of start date.
	 * @param startDate
	 * @param registrationDate
	 * @return boolean
	 */
	private boolean isDateValid(LocalDate startDate, LocalDate dateToBeValidated) {
		LocalDate stDtBefore3Days = startDate.minusDays(3);
		int compare = dateToBeValidated.compareTo(stDtBefore3Days);
		return (compare < 0);
	}

	/**
	 * Checks if the participant is already enrolled for a course with given course id.
	 * @param id
	 * @param name
	 * @return boolean
	 */
	private boolean isAlreadyEnrolledForCourse(long id, String name) {
		ParticipantEntity participantEntity = new ParticipantEntity();
		participantEntity.setCourseId(id);
		participantEntity.setName(name);
		return participantRepository.exists(Example.of(participantEntity));
	}

}
