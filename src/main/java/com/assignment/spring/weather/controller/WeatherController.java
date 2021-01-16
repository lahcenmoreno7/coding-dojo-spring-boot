package com.assignment.spring.weather.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.spring.weather.exception.ResourceNotFoundException;
import com.assignment.spring.weather.exception.WeatherServiceException;
import com.assignment.spring.weather.response.WeatherResponse;
import com.assignment.spring.weather.service.WeatherService;

@RestController
public class WeatherController {

	private final Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);

	@Autowired
	private WeatherService weatherService;

	@GetMapping("/weather")
	public ResponseEntity<WeatherResponse> weather(@RequestParam(required = false, name = "city") String city)
			throws ResourceNotFoundException, WeatherServiceException {

		LOGGER.info("Controller get weather : {} ", city);
		ResponseEntity<WeatherResponse> response;

		response = weatherService.getWeatherByCity(city);

		if (response == null) {
			throw new ResourceNotFoundException("Open weather response invalid or City not found");
 
		} else {

			weatherService.saveWeather(response.getBody());
		}

		return response;

	}
}
