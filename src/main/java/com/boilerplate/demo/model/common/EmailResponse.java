package com.boilerplate.demo.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailResponse {
	
	@JsonProperty(value = "id")
	private String id;
	
	@JsonProperty(value = "message")
	private String message;

	@JsonProperty(value = "trace")
	private String trace;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}
}
