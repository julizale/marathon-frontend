package com.marathonfront.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marathonfront.config.ApiConfig;
import com.marathonfront.domain.Race;
import com.marathonfront.domain.Team;
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

public class RaceService {

    private static RaceService raceService;
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(RaceService.class);
    private final String url = ApiConfig.apiUrl + "race";

    public static RaceService getInstance() {
        if (raceService == null) {
            raceService = new RaceService();
        }
        return raceService;
    }

    private RaceService() {
        restTemplate = new RestTemplate();
    }

    public Race getRace(long id) {
        try {
            return restTemplate.getForObject(url + "/" + id, Race.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new Race();
        }
    }

    public List<Race> getAllRaces() {

        try {
            Race[] racesResponse = restTemplate.getForObject(url, Race[].class);
            return new ArrayList<>(Optional.ofNullable(racesResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public void createNewRace(Race race) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String jsonContent = gson.toJson(race);

        HttpEntity<String> entity = new HttpEntity<String>(jsonContent, headers);
        LOGGER.info("Sending request to create race");
        try {
            restTemplate.postForObject(url, entity, String.class);
            LOGGER.info("New race created.");
        } catch (RestClientException e) {
            LOGGER.error("Rest client exception: " + e.getMessage(), e);
        }
    }

    public void delete(Race race) {

    }
}
