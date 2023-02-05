package com.marathonfront.service;

import com.marathonfront.config.ApiConfig;
import com.marathonfront.domain.Team;
import com.marathonfront.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
}
