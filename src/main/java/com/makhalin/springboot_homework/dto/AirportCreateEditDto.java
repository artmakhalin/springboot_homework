package com.makhalin.springboot_homework.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Value
@FieldNameConstants
public class AirportCreateEditDto {

    @Size(min = 3, max = 3, message = "Should contain exactly 3 letters")
    @Pattern(regexp = "[A-Z]+", message = "Should contain only English letters")
    String code;

    @NotNull
    Integer cityId;
}
