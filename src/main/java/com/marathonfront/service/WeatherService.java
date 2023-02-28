package com.marathonfront.service;

import com.marathonfront.config.ApiConfig;
import com.marathonfront.domain.weather.WeatherDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

public class WeatherService {

    private static WeatherService weatherService;
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherService.class);
    private final String url = ApiConfig.backendUrl + "weather/";

    public static WeatherService getInstance() {
        if (weatherService == null) {
            weatherService = new WeatherService();
        }
        return weatherService;
    }

    private WeatherService() {
        restTemplate = new RestTemplate();
    }

    public WeatherDay getWeatherDay(LocalDate date) {
        LOGGER.info("Sending request to weather api for weather on date: " + date);
        try {
            WeatherDay weather = restTemplate.getForObject(url + date, WeatherDay.class);
            LOGGER.info("Received: " + weather);
            return weather;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    private URI buildUrl(LocalDate date) {
        return UriComponentsBuilder.fromHttpUrl(ApiConfig.weatherUrl + date + "/" + date)
                .queryParam("unitGroup", "metric")
                .queryParam("elements", "datetime,temp,feelslike,precip,preciptype,windspeed")
                .queryParam("include", "days")
                .queryParam("key", ApiConfig.weatherKey)
                .queryParam("contentType", "json")
                .build()
                .encode()
                .toUri();
    }
}
