package com.marathonfront.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marathonfront.config.ApiConfig;
import com.marathonfront.domain.Performance;
import com.marathonfront.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

public class PerformanceService {

    private static PerformanceService performanceService;
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceService.class);
    private final String url = ApiConfig.apiUrl + "performance";

    public static PerformanceService getInstance() {
        if (performanceService == null) {
            performanceService = new PerformanceService();
        }
        return performanceService;
    }

    private PerformanceService() {
        restTemplate = new RestTemplate();
    }
    public List<Performance> getAllPerformances() {

        try {
            Performance[] perfResponse = restTemplate.getForObject(url, Performance[].class);
            return new ArrayList<>(Optional.ofNullable(perfResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public void savePerformance(Performance performance) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(performance);

        HttpEntity<String> entity = new HttpEntity<>(jsonContent, headers);
        LOGGER.info("Sending request to save performance");
        LOGGER.info("JSON: \n" + jsonContent);
        try {
            restTemplate.postForObject(url, entity, String.class);
            LOGGER.info("Performance saved successfully.");
        } catch (RestClientException e) {
            LOGGER.error("Rest client exception: " + e.getMessage(), e);
        }
    }

    public void delete(Performance performance) {
        restTemplate.delete(url + "/" + performance.getId());
    }
}
