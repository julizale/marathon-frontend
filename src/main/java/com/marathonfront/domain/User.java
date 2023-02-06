package com.marathonfront.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class User {

    private Long id;
    @NotBlank
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private LocalDate birthDate;
    private Sex sex;
    @NotBlank
    private String city;
    @NotBlank
    private String password;
    private long teamId;
    private long performanceId;

}
