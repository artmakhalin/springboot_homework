package com.makhalin.springboot_homework.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class ErrorResponse {

    String message;
}
