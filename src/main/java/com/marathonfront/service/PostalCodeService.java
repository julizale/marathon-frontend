package com.marathonfront.service;

import com.marathonfront.config.ApiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class PostalCodeService {

    private final String url = ApiConfig.backendUrl + "postal/";

    private static PostalCodeService postalCodeService;
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(PostalCodeService.class);

    public static PostalCodeService getInstance() {
        if (postalCodeService == null) {
            postalCodeService = new PostalCodeService();
        }
        return postalCodeService;
    }

    private PostalCodeService() {
        restTemplate = new RestTemplate();
    }

    public String getCity(String postalCode) {
        LOGGER.info("Sending request to postal code api for code " + postalCode);
        try {
            String city = restTemplate.getForObject(url + postalCode, String.class);
            LOGGER.info("Received: " + city);
            return city;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return "";
        }
    }
}
