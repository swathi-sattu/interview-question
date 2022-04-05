package com.example.demo.course.data;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ParticipantDetails {
	
    private String name;

    private LocalDate registrationDate;
}
