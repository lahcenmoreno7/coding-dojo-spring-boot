package com.assignment.spring.weather.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.assignment.spring.weather.ApplicationWeather;
import com.assignment.spring.weather.entity.WeatherEntity;
import com.assignment.spring.weather.exception.WeatherServiceException;
import com.assignment.spring.weather.response.WeatherResponse;
import com.assignment.spring.weather.service.impl.WeatherServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApplicationWeather.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherControllerTest {

		
	@MockBean
	private WeatherServiceImpl weatherServiceImpl;
	
	@MockBean
	private RestTemplate restTemplate;

    @Autowired
    private TestRestTemplate testRestTemplate;
    
	@Autowired
	private ObjectMapper objectMapper;

	@LocalServerPort
	private int port;

	@Value("${config-weather.api.url}")
	private String url;

	@Value("${config-weather.api.key}")
	private String key;
	
	@Value("classpath:data_Test/rotterdamWeather.json")
	private Resource weatherRotterdamResponse;

	@Before
	public void setUp() throws WeatherServiceException {
	
		Mockito.when(weatherServiceImpl.getWeatherByCity(Mockito.anyString())).thenReturn(Mockito.any());
	}

	@Test
	public void getWeatherForCity() throws Exception {

		final String expectedUrl = "http://api.openweathermap.org/data/2.5/weather?q=Rotterdam&APPID=" + key;
		
		final WeatherResponse weatherResponse = objectMapper.readValue(weatherRotterdamResponse.getFile(),
				WeatherResponse.class);

		final ResponseEntity<WeatherResponse> responseEntityForMock = ResponseEntity.ok(weatherResponse);
        
		Mockito.when(restTemplate.getForEntity(Mockito.anyString(),WeatherResponse.class))
				.thenReturn(responseEntityForMock);
		
        ResponseEntity<WeatherEntity> responseEntity = this.testRestTemplate.getForEntity(expectedUrl,
                WeatherEntity.class, port, "Rotterdam");
        
		final String expectedCity = "Rotterdam";
		final Double expectedTemperature = Double.valueOf(275.1);

		
		assertEquals("Status response OK", HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Same city name", expectedCity, responseEntityForMock.getBody().getName());
		assertTrue("Same temperature",
				expectedTemperature.equals(responseEntityForMock.getBody().getMain().getTemp()));


	}

}