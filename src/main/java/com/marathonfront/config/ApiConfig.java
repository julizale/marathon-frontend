package com.marathonfront.config;

import lombok.Getter;

import java.io.IOException;
import java.util.Properties;

@Getter
public class ApiConfig {

    static Properties conf;

    static {
        try {
            conf = PropertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String backendUrl = conf.getProperty("backendUrl");
    public static final String postalCodeUrl = conf.getProperty("postalCodeUrl");
    public static final String weatherUrl = conf.getProperty("weatherUrl");
    public static final String weatherKey = conf.getProperty("weatherKey");

    public ApiConfig() throws IOException {
    }
}
