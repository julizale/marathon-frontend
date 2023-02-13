package com.marathonfront.domain;

import com.marathonfront.domain.enumerated.Sex;
import lombok.Data;

@Data
public class StartlistDisplayObject {

    private Long performanceId;
    private Integer bibNumber;
    private String firstName;
    private String lastName;
    private int birthYear;
    private Sex sex;
    private String city;
    private String teamName;
}
