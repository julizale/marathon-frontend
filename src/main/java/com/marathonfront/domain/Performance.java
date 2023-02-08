package com.marathonfront.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
