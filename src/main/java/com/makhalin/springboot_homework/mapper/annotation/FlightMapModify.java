package com.makhalin.springboot_homework.mapper.annotation;

import org.mapstruct.Mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
@Mapping(target = "time", expression = "java(com.makhalin.springboot_homework.util.FlightTimeUtil.secFromHoursAndMin(object.getHours(), object.getMinutes()))")
@Mapping(target = "arrivalAirport", expression = FlightMapModify.GET_ARRIVAL_AIRPORT)
@Mapping(target = "departureAirport", expression = FlightMapModify.GET_DEPARTURE_AIRPORT)
@Mapping(target = "transitAirport", expression = FlightMapModify.GET_TRANSIT_AIRPORT)
@Mapping(target = "aircraft", expression = FlightMapModify.GET_AIRCRAFT)
public @interface FlightMapModify {

    String GET_ARRIVAL_AIRPORT = """
                            java(java.util.Optional.ofNullable(object.getArrivalAirportId())
                            .flatMap(airportRepository::findById)
                            .orElse(null))
            """;

    String GET_DEPARTURE_AIRPORT = """
                            java(java.util.Optional.ofNullable(object.getDepartureAirportId())
                            .flatMap(airportRepository::findById)
                            .orElse(null))
            """;

    String GET_TRANSIT_AIRPORT = """
                            java(java.util.Optional.ofNullable(object.getTransitAirportId())
                            .flatMap(airportRepository::findById)
                            .orElse(null))
            """;

    String GET_AIRCRAFT = """
                            java(java.util.Optional.ofNullable(object.getAircraftId())
                            .flatMap(aircraftRepository::findById)
                            .orElse(null))
            """;
}
