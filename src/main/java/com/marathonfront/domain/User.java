package com.marathonfront.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Sex sex;
    private String city;
    private long teamId;
    private long performanceId;

}
