package com.example.demo.course.data;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CourseRegistrationRequest {
    private Long courseId;

    private LocalDate registrationDate;

    private String name;

}
