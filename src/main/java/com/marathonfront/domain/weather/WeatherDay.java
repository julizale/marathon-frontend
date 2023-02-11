package com.marathonfront.domain.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDay {

    @JsonProperty("datetime")
    LocalDate datetime;

    @JsonProperty("temp")
    double temp;

    @JsonProperty("feelslike")
    double feelslike;

    @JsonProperty("precip")
    double precip;

    @JsonProperty("windspeed")
    double windspeed;
}
