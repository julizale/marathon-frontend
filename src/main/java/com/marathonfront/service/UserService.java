package com.marathonfront.service;

import com.marathonfront.config.ApiConfig;
import com.marathonfront.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class UserService {

    private static UserService userService;
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final String url = ApiConfig.apiUrl + "user";

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    private UserService() {
        restTemplate = new RestTemplate();
    }
    public List<User> getAllUsers() {

        try {
            User[] usersResponse = restTemplate.getForObject(url, User[].class);
            return new ArrayList<>(Optional.ofNullable(usersResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
