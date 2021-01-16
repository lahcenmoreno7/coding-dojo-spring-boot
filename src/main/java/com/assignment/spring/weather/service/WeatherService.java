package com.assignment.spring.weather.service;

import org.springframework.http.ResponseEntity;

import com.assignment.spring.weather.entity.WeatherEntity;
import com.assignment.spring.weather.exception.WeatherServiceException;
import com.assignment.spring.weather.response.WeatherResponse;

public interface WeatherService {
   
    ResponseEntity<WeatherResponse> getWeatherByCity(String city) throws WeatherServiceException;

    WeatherEntity saveWeather(WeatherResponse weatherResponse) throws WeatherServiceException;

}
