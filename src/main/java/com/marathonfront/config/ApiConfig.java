package com.marathonfront.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class ApiConfig {

    @Value("${api.url}")
    private String apiUrl;
}
