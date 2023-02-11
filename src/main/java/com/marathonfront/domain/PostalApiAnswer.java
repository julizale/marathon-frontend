package com.marathonfront.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostalApiAnswer {

    @JsonProperty("miejscowosc")
    private String miejscowosc;
}
