package com.makhalin.springboot_homework.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CrewFilter {

    Integer startYear;
    String aircraftModel;
}
