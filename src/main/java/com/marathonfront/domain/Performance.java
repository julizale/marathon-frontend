package com.marathonfront.domain;

import com.marathonfront.domain.enumerated.StartStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Performance {

    private long id;
    private long userId;
    private long raceId;
    private boolean paid;
    private Integer bibNumber;
    private StartStatus status;
    private BigDecimal timeGross;
    private BigDecimal timeNet;
}
