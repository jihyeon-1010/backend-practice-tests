package com.example.service;

import com.example.domain.Weather;
import com.example.properties.WeatherConfig;
import com.example.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final WeatherConfig weatherConfig;
    private final WeatherRepository weatherRepository;

    // 단기예보 저장
    @Transactional
    public void saveWeatherData(LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.BASIC_ISO_DATE);

        String requestUrl = String.format("%s?serviceKey=%s&numOfRows=10&pageNo=1&dataType=JSON&base_date=%s&base_time=0500&nx=61&ny=130",
                weatherConfig.getBaseUrl(), weatherConfig.getApiKey(), formattedDate);

        String response = restTemplate.getForObject(requestUrl, String.class);
        log.info(response);

        List<Weather> weathers = parseWeatherData(response);
        weatherRepository.saveAll(weathers);
    }

    // 단기예보 조회
    @Transactional(readOnly = true)
    public List<Weather> getWeatherData(LocalDate date) {
        return weatherRepository.findByBaseDate(date);
    }

    // JSON 응답을 파싱하여 Weather 객체 리스트로 변환
    private List<Weather> parseWeatherData(String jsonData) {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray items = jsonObject.getJSONObject("response")
                .getJSONObject("body")
                .getJSONObject("items")
                .getJSONArray("item");

        List<Weather> weathers = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);

            LocalDate baseDate = LocalDate.parse(item.getString("baseDate"), DateTimeFormatter.BASIC_ISO_DATE);
            LocalTime baseTime = LocalTime.parse(item.getString("baseTime"), DateTimeFormatter.ofPattern("HHmm"));
            LocalDate fcstDate = LocalDate.parse(item.getString("fcstDate"), DateTimeFormatter.BASIC_ISO_DATE);
            LocalTime fcstTime = LocalTime.parse(item.getString("fcstTime"), DateTimeFormatter.ofPattern("HHmm"));
            int nx = item.getInt("nx");
            int ny = item.getInt("ny");
            String category = item.getString("category");
            String fcstValue = item.getString("fcstValue");

            Weather weather = new Weather(baseDate, baseTime, nx, ny, fcstDate, fcstTime, category, fcstValue);
            weathers.add(weather);
        }
        return weathers;
    }

}

