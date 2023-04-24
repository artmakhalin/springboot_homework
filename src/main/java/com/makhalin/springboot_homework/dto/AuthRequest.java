package com.makhalin.springboot_homework.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class AuthRequest {

    @NotBlank
    String username;

    @NotBlank
    String password;
}
