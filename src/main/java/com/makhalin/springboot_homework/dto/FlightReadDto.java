package com.makhalin.springboot_homework.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class FlightReadDto {

    Long id;
    String flightNo;
    AirportReadDto departureAirport;
    AirportReadDto arrivalAirport;
    AirportReadDto transitAirport;
    LocalDate departureDate;
    Integer hours;
    Integer minutes;
    AircraftReadDto aircraft;
}
