package com.example.demo.course.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.course.data.CancelParticipantEnrollmentRequest;
import com.example.demo.course.data.CourseDetailsResponse;
import com.example.demo.course.data.CourseRegistrationRequest;
import com.example.demo.course.data.CourseRequest;
import com.example.demo.course.data.CourseResponse;
import com.example.demo.course.exceptions.CourseNotFoundException;
import com.example.demo.course.exceptions.CourseRegistrationException;
import com.example.demo.course.exceptions.InputValidationException;
import com.example.demo.course.service.CourseService;
import com.example.demo.course.util.ValidatorUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/courses")
@Tag(name = "Course API", description = "Course API")
public class CourseController {

	@Operation(summary = "Healthcheck", description = "To check the health status of the application")
	@PostMapping(path = "/healthcheck")
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Autowired
	CourseService service;
	
	@Autowired
	ValidatorUtil validator;

	@Operation(summary = "Creates new Course", description = "Creates new course with given request details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created successfully", content = @Content(schema = @Schema(implementation = CourseResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal Service Error") })
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<CourseResponse> createCourse(@RequestBody @Validated CourseRequest courseRequest) throws InputValidationException
	{
		validator.validateCourseRequest(courseRequest);
		CourseResponse courseDetails = service.createCourse(courseRequest);

		return new ResponseEntity<>(courseDetails, HttpStatus.CREATED);
	}

	@GetMapping("/{title}")
	@Operation(summary = "Course by Title", description = "Gets the course details by given title")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = CourseResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal Service Error") })
	public ResponseEntity<List<CourseResponse>> getCourseByTitle(
			@Parameter(description = "Title of the course cannot be empty.", required = true) @PathVariable("title") @NotNull @NotBlank String title)
	{
		List<CourseResponse> entity = service.getCourseByTitle(title);

		return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("details/{id}")
	@Operation(summary = "Get Course Details", description = "Gets course details or given course id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Registration was successful,"),
			@ApiResponse(responseCode = "200", description = "successful operation"),
			@ApiResponse(responseCode = "500", description = "Internal Service Error") })
	public ResponseEntity<CourseDetailsResponse> getCourseDetails(@PathVariable long id)
	{
		CourseDetailsResponse detailsResponse = service.getCourseDetails(id);
		return new ResponseEntity<>(detailsResponse, new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping("/{id}/add")
	@Operation(summary = "Course Registration", description = "Registers a participant for a course")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Registration was successful,"),
			@ApiResponse(responseCode = "400", description = "<name> already enrolled in course"),
			@ApiResponse(responseCode = "400", description = "RegistrationDate is not valid"),
			@ApiResponse(responseCode = "400", description = "Course is Full"),
			@ApiResponse(responseCode = "404", description = "Course doesnt exist") })
	public ResponseEntity<CourseDetailsResponse> courseRegistration(@PathVariable long id,
			@RequestBody @Validated CourseRegistrationRequest registrationRequest) throws CourseNotFoundException, CourseRegistrationException, InputValidationException  {
		validator.validateCourseId(id, registrationRequest.getCourseId());
		validator.validateCourseRegistrationRequest(registrationRequest);
		CourseDetailsResponse updated = service.courseRegistration(id, registrationRequest);
		return new ResponseEntity<CourseDetailsResponse>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	
	@PostMapping("/{id}/remove")
	@Operation(summary = "Cancel Course Enrollment", description = "Cancels the user enrollment for the given courseId")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cancellation was successful,"),
			@ApiResponse(responseCode = "400", description = "RegistrationDate is not valid"),
			@ApiResponse(responseCode = "400", description = "Course is Full"),
			@ApiResponse(responseCode = "404", description = "Course doesnt exist") })
	public ResponseEntity<CourseDetailsResponse> cancelEnrollment(@PathVariable long id,
			@RequestBody @Validated CancelParticipantEnrollmentRequest cancelRequest) throws CourseNotFoundException, CourseRegistrationException, InputValidationException  {
		validator.validateCourseId(id, cancelRequest.getCourseId());
		validator.validateCancelParticipantEnrollmentRequest(cancelRequest);
		CourseDetailsResponse updated = service.cancelEnrollment(id, cancelRequest);
		return new ResponseEntity<CourseDetailsResponse>(updated, new HttpHeaders(), HttpStatus.OK);
	}

}
