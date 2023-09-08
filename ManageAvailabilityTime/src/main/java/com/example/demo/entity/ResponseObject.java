package com.example.demo.entity;

import org.springframework.http.HttpStatus;

public class ResponseObject {

	private Object response;
	private String message;
	private HttpStatus status;

	public ResponseObject(Object response, String message, HttpStatus status) {
		super();
		this.response = response;
		this.message = message;
		this.status = status;
	}

	public Object getResponse() {
		return response;
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return this.status.value();
	}	
}
