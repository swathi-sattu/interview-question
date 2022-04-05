package com.example.demo.course.controller;

import java.util.List;

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

import com.example.demo.course.data.CourseDetailsResponse;
import com.example.demo.course.data.CourseRequest;
import com.example.demo.course.data.CourseResponse;
import com.example.demo.course.service.CourseService;
import com.example.demo.persistence.RecordNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(produces = "application/json")
@Tag(name = "Course API", description = "Course API")
public class CourseController {

	@Operation(summary = "Healthcheck", description = "To check the health status of the application")
	@PostMapping(path = "/healthcheck")
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Autowired
	CourseService service;

	@Operation(summary = "Create new Course", description = "Creates new course with given request details", tags = {
			"contact" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = CourseResponse.class))),
			@ApiResponse(responseCode = "500", description = "DB not available") })
	@PostMapping(value = "/courses")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<CourseResponse> createCourse(@RequestBody @Validated CourseRequest courseRequest)
			throws RecordNotFoundException {

		CourseResponse courseDetails = service.createCourse(courseRequest);

		return new ResponseEntity<>(courseDetails, HttpStatus.CREATED);
	}

	@GetMapping("/{title}")
	@Operation(summary = "Course by Title", description = "Gets the course details by given title", tags = { "title" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = CourseResponse.class))),
			@ApiResponse(responseCode = "500", description = "Invalid title supplied") })
	public ResponseEntity<List<CourseResponse>> getCourseByTitle(
			@Parameter(description = "Title of the course cannot be empty.", required = true) @PathVariable("title") @NotNull @NotBlank String title)
			throws RecordNotFoundException {
		List<CourseResponse> entity = service.getCourseByTitle(title);

		return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("details/{id}")
	@Operation(summary = "Get Course Details", description = "Gets course details or given course id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Registration was successful,"),
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = CourseResponse.class))),
			@ApiResponse(responseCode = "500", description = "Invalid id supplied") })
	public ResponseEntity<CourseDetailsResponse> getCourseDetails(@PathVariable long id)
			throws RecordNotFoundException {
		CourseDetailsResponse detailsResponse = service.getCourseDetails(id);
		return new ResponseEntity<>(detailsResponse, new HttpHeaders(), HttpStatus.OK);
	}

}
