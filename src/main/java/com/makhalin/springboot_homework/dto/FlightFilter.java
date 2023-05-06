package com.makhalin.springboot_homework.dto;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@Builder
@FieldNameConstants
public class FlightFilter {

    Integer year;
    Integer month;
    String crewEmail;
    String arrivalAirport;
    String departureAirport;
    String aircraft;
}
