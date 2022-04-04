package com.example.demo.course.data;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CourseResponse {
	
	private Long id;
	
    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private String capacity;
    
    private String remaining;

}
