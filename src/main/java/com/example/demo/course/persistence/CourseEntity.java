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
@Table(name = "TBL_COURSES")
public class CourseEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "capacity")
	private Integer capacity;

	@Column(name = "remaining")
	private Integer remaining;

	@Override
	public String toString() {
		return "EmployeeEntity [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", capacity=" + capacity + ", remaining=" + remaining + "]";
	}
}
