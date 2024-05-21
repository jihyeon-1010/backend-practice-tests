package com.example.controller;

import com.example.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sync")
public class SyncController {

    private final WeatherService weatherService;

    // 단기예보 저장
    @PostMapping("/weather")
    public void syncWeatherData(@RequestParam LocalDate date) {
        weatherService.saveWeatherData(date);
    }

}