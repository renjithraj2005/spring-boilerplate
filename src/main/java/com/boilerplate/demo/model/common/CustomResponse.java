package com.boilerplate.demo.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

@JsonInclude(Include.NON_NULL) 
public class CustomResponse {

	@JsonProperty("message")
	private String message;
	
	@JsonProperty("data")
	private Object body;

	@JsonIgnore
	private HttpStatus status;

	public static CustomResponse builder(){
		return new CustomResponse();
	}

	public CustomResponse message(String message) {
		this.message = message;
		return this;
	}

	public String getMessage() {
		return this.message;
	}
	
	public CustomResponse body(Object body) {
		this.body = body;
		return this;
	}
	
	public Object getBody() {
		return this.body;
	}

	public CustomResponse status(HttpStatus status){
		this.status = status;
		return this;
	}

	public HttpStatus getStatus() { return this.status; }
}
