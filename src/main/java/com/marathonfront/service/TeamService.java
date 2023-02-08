package com.marathonfront.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marathonfront.config.ApiConfig;
import com.marathonfront.domain.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class TeamService {

    private static TeamService teamService;
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamService.class);
    private final String url = ApiConfig.apiUrl + "team";

    public static TeamService getInstance() {
        if (teamService == null) {
            teamService = new TeamService();
        }
        return teamService;
    }

    private TeamService() {
        restTemplate = new RestTemplate();
    }
    public List<Team> getAllTeams() {

        try {
            Team[] teamsResponse = restTemplate.getForObject(url, Team[].class);
            return new ArrayList<>(Optional.ofNullable(teamsResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public Team getTeam(long teamId) {
        try {
            return restTemplate.getForObject(url + "/" + teamId, Team.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new Team();
        }
    }

    public void createNewTeam(Team team) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String jsonContent = gson.toJson(team);

        HttpEntity<String> entity = new HttpEntity<>(jsonContent, headers);
        LOGGER.info("Sending request to create race");
        try {
            restTemplate.postForObject(url, entity, String.class);
            LOGGER.info("New race created.");
        } catch (RestClientException e) {
            LOGGER.error("Rest client exception: " + e.getMessage(), e);
        }
    }

    public void delete(Team team) {

    }
}
