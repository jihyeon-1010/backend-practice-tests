package com.example.repository;

import com.example.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    List<Weather> findByBaseDate(LocalDate date);

}