package com.makhalin.springboot_homework.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.Size;

@Value
@FieldNameConstants
public class AircraftCreateEditDto {

    @Size(min = 2, max = 32)
    String model;
}
