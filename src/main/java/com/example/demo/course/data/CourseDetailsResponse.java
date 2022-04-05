package com.example.demo.course.data;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class CourseDetailsResponse {
	
	private Long id;
	
    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer capacity;
    
    private Integer remaining;
    
    private List<ParticipantDetails> participantDetails;

}
