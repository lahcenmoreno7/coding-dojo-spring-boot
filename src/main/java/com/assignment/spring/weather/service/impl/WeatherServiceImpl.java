package com.assignment.spring.weather.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.assignment.spring.weather.entity.WeatherEntity;
import com.assignment.spring.weather.exception.WeatherServiceException;
import com.assignment.spring.weather.repository.WeatherRepository;
import com.assignment.spring.weather.response.WeatherResponse;
import com.assignment.spring.weather.service.WeatherService;

@Service
public class WeatherServiceImpl implements WeatherService {

	private final Logger LOGGER = LoggerFactory.getLogger(WeatherServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WeatherRepository weatherRepository;
	
    
	@Value("${config-weather.api.url}")
	private String url;
	
	@Value("${config-weather.api.key}")
	private String key;

	public ResponseEntity<WeatherResponse> getWeatherByCity(String city) throws WeatherServiceException {
		
		 LOGGER.info("getWeatherByCity {}", city);
		 
		 String urlService = url.replace("{city}", city).replace("{appid}", key);
		 return restTemplate.getForEntity(urlService, WeatherResponse.class);
	}
	
	public WeatherEntity saveWeather(WeatherResponse weatherResponse) throws WeatherServiceException {
		
		LOGGER.info("saveWeather {}");
		
		WeatherEntity weather = new WeatherEntity();
		if (weatherResponse != null) {
			weather.setCity(weatherResponse.getName());
			weather.setCountry(weatherResponse.getSys().getCountry());
			weather.setTemperature(weatherResponse.getMain().getTemp());
			
        }else {
        	throw new WeatherServiceException();
        }
		
		return weatherRepository.save(weather);

	}

}
