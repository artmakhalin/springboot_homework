package com.makhalin.springboot_homework.dto;

import com.makhalin.springboot_homework.entity.ClassOfService;
import lombok.Value;

@Value
public class FlightCrewReadDto {

    Long id;
    FlightReadDto flight;
    CrewReadDto crew;
    ClassOfService classOfService;
    Boolean isTurnaround;
    Boolean isPassenger;
}
