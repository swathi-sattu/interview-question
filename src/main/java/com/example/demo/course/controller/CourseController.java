package com.example.demo.course.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
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

}
