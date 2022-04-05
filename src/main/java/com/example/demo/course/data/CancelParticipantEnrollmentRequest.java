package com.example.demo.course.data;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CancelParticipantEnrollmentRequest {

    private Long courseId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate cancelDate;

    private String name;

}
