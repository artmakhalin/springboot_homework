package com.makhalin.springboot_homework.mapper.annotation;

import org.mapstruct.Mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
@Mapping(target = "flight", expression = FlightCrewMapModify.GET_FLIGHT)
@Mapping(target = "crew", expression = FlightCrewMapModify.GET_CREW)
public @interface FlightCrewMapModify {

    String GET_FLIGHT = """
                            java(java.util.Optional.ofNullable(object.getFlightId())
                            .flatMap(flightRepository::findById)
                            .orElse(null))
            """;

    String GET_CREW = """
                            java(java.util.Optional.ofNullable(object.getCrewId())
                            .flatMap(crewRepository::findById)
                            .orElse(null))
            """;
}
