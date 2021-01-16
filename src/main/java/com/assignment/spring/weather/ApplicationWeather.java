package com.assignment.spring.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan("com.assignment.spring.weather")
@Configuration
public class ApplicationWeather {

	private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationWeather.class);
	 
	public static void main(String[] args) {
		LOGGER.info(" Launching weather application ");
		SpringApplication.run(ApplicationWeather.class, args);
	}

}
