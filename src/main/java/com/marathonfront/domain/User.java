package com.marathonfront.domain;

import com.marathonfront.domain.enumerated.Sex;
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
    private String password;
    private Long teamId;
    private Long performanceId;

}
