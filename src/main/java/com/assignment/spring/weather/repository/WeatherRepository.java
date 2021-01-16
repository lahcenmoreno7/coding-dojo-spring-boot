package com.assignment.spring.weather.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assignment.spring.weather.entity.WeatherEntity;

@Repository
public interface WeatherRepository extends CrudRepository<WeatherEntity, Integer> {
}
