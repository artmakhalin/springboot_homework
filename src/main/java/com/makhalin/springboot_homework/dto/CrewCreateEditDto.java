package com.makhalin.springboot_homework.dto;

import com.makhalin.springboot_homework.entity.Role;
import com.makhalin.springboot_homework.validation.OnCreate;
import com.makhalin.springboot_homework.validation.OnUpdate;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.Past;
import javax.validation.constraints.Future;
import java.time.LocalDate;

@Value
@FieldNameConstants
public class CrewCreateEditDto {

    @Email(groups = {OnCreate.class, OnUpdate.class})
    String email;

    @NotBlank(groups = OnCreate.class)
    String rawPassword;

    @Size(min = 3, max = 128, groups = {OnCreate.class, OnUpdate.class})
    String firstname;

    @Size(min = 3, max = 128, groups = {OnCreate.class, OnUpdate.class})
    String lastname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(groups = {OnCreate.class, OnUpdate.class})
    LocalDate birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(groups = {OnCreate.class, OnUpdate.class})
    LocalDate employmentDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(groups = {OnCreate.class, OnUpdate.class})
    LocalDate mkkDate;

    Role role = Role.USER;
}
