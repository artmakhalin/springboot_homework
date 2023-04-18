package com.makhalin.springboot_homework.dto;

import com.makhalin.springboot_homework.entity.Role;
import lombok.Value;

import java.time.LocalDate;

@Value
public class CrewReadDto {

    Integer id;
    String email;
    String firstname;
    String lastname;
    LocalDate birthDate;
    LocalDate employmentDate;
    LocalDate mkkDate;
    Role role;
}
