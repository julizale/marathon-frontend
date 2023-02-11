package com.marathonfront.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Performance {

    private long id;
    private long userId;
    private long raceId;
    private boolean paid;
    private int bibNumber;
    private StartStatus status;
    private BigDecimal timeGross;
    private BigDecimal timeNet;
}
