package com.marathonfront.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertiesLoader {

    public static Properties loadProperties() throws IOException {
        Properties configuration = new Properties();
        InputStream inputStream = PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream("application.properties");
        configuration.load(inputStream);
        Objects.requireNonNull(inputStream).close();
        return configuration;
    }
}
