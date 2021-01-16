package com.assignment.spring.weather.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.assignment.spring.weather.ApplicationWeather;
import com.assignment.spring.weather.api.Main;
import com.assignment.spring.weather.api.Sys;
import com.assignment.spring.weather.entity.WeatherEntity;
import com.assignment.spring.weather.repository.WeatherRepository;
import com.assignment.spring.weather.response.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationWeather.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherServiceImplTest {

	@MockBean
	private RestTemplate restTemplate;

	@Autowired
	private WeatherService weatherService;

	@MockBean
	private WeatherRepository weatherRepository;

	@Value("${config-weather.api.url}")
	private String url;

	@Value("${config-weather.api.key}")
	private String key;

	@Value("classpath:data_Test/rotterdamWeather.json")
	private Resource weatherRotterdamResponse;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void getWeatherForCityOK() throws Exception {
		final WeatherResponse expectedWeatherResponse = new WeatherResponse();
		final ResponseEntity<WeatherResponse> expectedResponseEntity = ResponseEntity.ok(expectedWeatherResponse);

		final String expectedUrl = "http://api.openweathermap.org/data/2.5/weather?q=Rotterdam&APPID=" + key;

		Mockito.when(restTemplate.getForEntity(Mockito.eq(expectedUrl), Mockito.eq(WeatherResponse.class)))
				.thenReturn(expectedResponseEntity);

		weatherService.getWeatherByCity("Rotterdam");

		Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(Mockito.anyString(),
				Mockito.eq(WeatherResponse.class));

	}

	@Test
	public void save_weather_OK() throws Exception {

		final String expectedName = "Rotterdam";
		final String expectedCountry = "NL";
		final Double expectedTemperature = Double.valueOf(275.1);

		final WeatherResponse weatherResponse = objectMapper.readValue(weatherRotterdamResponse.getFile(),
				WeatherResponse.class);

		final ResponseEntity<WeatherResponse> responseEntityForMock = ResponseEntity.ok(weatherResponse);

		Mockito.when(weatherRepository.save(Mockito.any(WeatherEntity.class))).thenReturn(new WeatherEntity());

		assertThat(weatherService.saveWeather(weatherResponse), is(notNullValue()));
		assertEquals("Same city", expectedName, responseEntityForMock.getBody().getName());
		assertEquals("Same Country", expectedCountry, responseEntityForMock.getBody().getSys().getCountry());
		assertEquals("Same Temperature ", expectedTemperature, responseEntityForMock.getBody().getMain().getTemp());
		Mockito.verify(weatherRepository, Mockito.times(1)).save(Mockito.any(WeatherEntity.class));
	}

}
