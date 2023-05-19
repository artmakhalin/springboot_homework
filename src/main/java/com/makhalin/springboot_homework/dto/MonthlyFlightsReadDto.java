package com.makhalin.springboot_homework.dto;

import lombok.Value;

import java.util.List;

@Value
public class MonthlyFlightsReadDto {

    Integer hours;
    Integer minutes;
    List<FlightReadDto> flights;
}
