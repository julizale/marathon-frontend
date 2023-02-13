package com.marathonfront.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marathonfront.adapter.LocalDateAdapter;
import com.marathonfront.config.ApiConfig;
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

public class UserService {

    private static UserService userService;
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final String url = ApiConfig.backendUrl + "user";

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    private UserService() {
        restTemplate = new RestTemplate();
    }

    public User getUser(long id) {
        try {
            return restTemplate.getForObject(url + "/" + id, User.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new User();
        }
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

    public void saveUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(user);

        HttpEntity<String> entity = new HttpEntity<>(jsonContent, headers);
        LOGGER.info("Sending request to save user");
        LOGGER.info(jsonContent);
        try {
            restTemplate.postForObject(url, entity, String.class);
            LOGGER.info("User saved.");
        } catch (RestClientException e) {
            LOGGER.error("Rest client exception: " + e.getMessage(), e);
        }
    }

    public void delete(User user) {
        try {
            restTemplate.delete(url + "/" + user.getId());
            LOGGER.info("User deleted.");
        } catch (RestClientException e) {
            LOGGER.error("Rest client exception: " + e.getMessage(), e);
        }
    }
}
