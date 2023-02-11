package com.marathonfront.domain.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDay {

    @JsonProperty("datetime")
    LocalDate date;

    @JsonProperty("temp")
    double temperature;

    @JsonProperty("feelslike")
    double feelslike;

    @JsonProperty("precip")
    double preciptation;

    @JsonProperty("windspeed")
    double windspeed;
}
