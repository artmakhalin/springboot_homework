package com.makhalin.springboot_homework.dto;

import lombok.Value;

import java.util.Map;

@Value
public class YearStatisticsReadDto {

    Map<Integer, FlightTimeReadDto> statistics;
    FlightTimeReadDto totalTime;
}
