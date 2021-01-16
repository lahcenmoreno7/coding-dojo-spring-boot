package com.assignment.spring.weather.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "code", "message" })
public class ResponseMessage {

	@JsonProperty("code")
	private Integer code;

	@JsonProperty("message")
	private String message;

	public ResponseMessage() {
	}

	public ResponseMessage(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
