package com.makhalin.springboot_homework.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FlightFilter {

    Integer year;
    Integer month;
    String crewEmail;
    String arrivalAirport;
    String departureAirport;
    String aircraft;
}
