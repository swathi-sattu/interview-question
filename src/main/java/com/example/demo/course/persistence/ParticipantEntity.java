package com.example.demo.course.persistence;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TBL_PARTICIPANTS")
public class ParticipantEntity {

	@Id
	@GeneratedValue
	private Long participantId;

	@Column(name = "course_id")
	private Long courseId;

	@Column(name = "registration_date")
	private LocalDate registrationDate;

	@Column(name = "cancel_date")
	private LocalDate cancelDate;

	@Column(name = "name")
	private String name;

	@Override
	public String toString() {
		return "EmployeeEntity [participantId=" + participantId + ", " + ", courseid=" + courseId
				+ ", registrationDate=" + registrationDate + ", name=" + name + ", cancelDate=" + cancelDate + "]";
	}

}
