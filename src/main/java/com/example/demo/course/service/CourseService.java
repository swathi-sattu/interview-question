package com.example.demo.course.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.course.data.CourseDetailsResponse;
import com.example.demo.course.data.CourseRequest;
import com.example.demo.course.data.CourseResponse;
import com.example.demo.course.data.ParticipantDetails;
import com.example.demo.course.persistence.CourseEntity;
import com.example.demo.course.persistence.CourseRepository;
import com.example.demo.course.persistence.ParticipantEntity;
import com.example.demo.course.persistence.ParticipantsRepository;
import com.example.demo.persistence.RecordNotFoundException;

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
	public CourseResponse createCourse(CourseRequest request) throws RecordNotFoundException {
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
		CourseDetailsResponse courseDetailsResponse = new CourseDetailsResponse();
		Optional<CourseEntity> course = repository.findById(id);
		if (course.isPresent()) {
			populateParticipantsDetails(id, courseDetailsResponse, course.get());
		}
		return courseDetailsResponse;
	}

	/**
	 * Populates the participants details for the given course id and return the
	 * course details
	 */
	private void populateParticipantsDetails(long id, CourseDetailsResponse courseDetailsResponse,
			CourseEntity course) {
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
	}

}
