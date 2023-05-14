package com.makhalin.springboot_homework.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class CrewAircraftReadDto {

    Long id;
    LocalDate permitDate;
    CrewReadDto crew;
    AircraftReadDto aircraft;
}
