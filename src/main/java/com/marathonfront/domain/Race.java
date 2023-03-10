package com.marathonfront.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Race {

    private long id;
    private String name;
    private Integer distance;
    private BigDecimal price;
}
