package com.assignment.spring.weather.exception;

public class WeatherServiceException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public WeatherServiceException() {
		super();
	}

	public WeatherServiceException(final String message) {
		super(message);
	}
}
