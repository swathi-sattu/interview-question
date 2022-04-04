package com.example.demo.course.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.course.data.CourseRequest;
import com.example.demo.course.data.CourseResponse;
import com.example.demo.course.persistence.CourseEntity;
import com.example.demo.course.persistence.CourseRepository;
import com.example.demo.course.persistence.ParticipantsRepository;
import com.example.demo.persistence.RecordNotFoundException;

@Service
public class CourseService {
	@Autowired
	CourseRepository repository;

	@Autowired
	ParticipantsRepository participantRepository;

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

}
