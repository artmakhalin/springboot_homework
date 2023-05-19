package com.makhalin.springboot_homework.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
@FieldNameConstants
public class AirportCreateEditDto {

    @Pattern(regexp = "[A-Z]{3}", message = "Should contain only 3 English letters")
    String code;

    @NotNull
    Integer cityId;
}
