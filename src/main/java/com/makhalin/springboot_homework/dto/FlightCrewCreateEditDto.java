package com.makhalin.springboot_homework.dto;

import com.makhalin.springboot_homework.entity.ClassOfService;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Value
@FieldNameConstants
public class FlightCrewCreateEditDto {

    @NotNull
    Long flightId;

    @NotNull
    Integer crewId;

    ClassOfService classOfService;

    Boolean isTurnaround;

    Boolean isPassenger;
}
