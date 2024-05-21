package com.example.controller;

import com.example.domain.Weather;
import com.example.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inquire")
public class InquireController {

    private final WeatherService weatherService;

    // 단기예보 조회
    @GetMapping("/weather")
    public ResponseEntity<List<Weather>> syncWeatherData(@RequestParam LocalDate date) {
        List<Weather> weathers = weatherService.getWeatherData(date);

        // 데이터가 없는 경우 Http status 204 반환
        if (weathers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        // 데이터가 있는 경우 데이터 반환
        return ResponseEntity.status(HttpStatus.OK).body(weathers);
    }

}