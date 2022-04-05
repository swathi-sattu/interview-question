package com.example.demo.course.data;

import org.springframework.stereotype.Component;

@Component
public class ExceptionResponseEntity {

	private Integer status;
	private String message;
	
	public Integer getStatus() {
		return status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Integer setStatus(Integer status) {
		return this.status = status;
	}
	
	public String setMessage(String message) {
		return this.message = message;
	}
}
