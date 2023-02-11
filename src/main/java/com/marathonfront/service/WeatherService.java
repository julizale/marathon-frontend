package com.marathonfront.service;

import com.marathonfront.config.ApiConfig;
import com.marathonfront.domain.weather.Weather;
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

    public static WeatherService getInstance() {
        if (weatherService == null) {
            weatherService = new WeatherService();
        }
        return weatherService;
    }

    private WeatherService() {
        restTemplate = new RestTemplate();
    }

    public Weather getWeather(LocalDate date) {
        LOGGER.info("Sending request to weather api for weather " + date);
        try {
            URI url = buildUrl(date);
            LOGGER.info("Built url :\n" + url);
            Weather weatherResponse = restTemplate.getForObject(url, Weather.class);
            LOGGER.info("Received: " + weatherResponse);
            return weatherResponse;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new Weather();
        }
    }

    private URI buildUrl(LocalDate date) {
        return UriComponentsBuilder.fromHttpUrl(ApiConfig.weatherUrl1 + date + "/" + date)
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
