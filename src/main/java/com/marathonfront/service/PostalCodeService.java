package com.marathonfront.service;

import com.marathonfront.config.ApiConfig;
import com.marathonfront.domain.PostalApiAnswer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class PostalCodeService {

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
            PostalApiAnswer[] postalResponse = restTemplate.getForObject(ApiConfig.postalCodeUrl + postalCode,
                   PostalApiAnswer[].class);
            String miejscowosc = Objects.requireNonNull(postalResponse)[0].getMiejscowosc();
            LOGGER.info("Received: " + miejscowosc);
            return miejscowosc;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return "";
        }
    }
}
