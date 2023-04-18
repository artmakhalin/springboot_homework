package com.makhalin.springboot_homework.dto;

import com.makhalin.springboot_homework.entity.Role;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Value
public class CrewCreateEditDto {

    @Email
    String email;

    @NotBlank
    String rawPassword;

    @Size(min = 3, max = 128)
    String firstname;

    @Size(min = 3, max = 128)
    String lastname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    LocalDate birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    LocalDate employmentDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    LocalDate mkkDate;

    Role role = Role.USER;
}
