package com.makhalin.springboot_homework.mapper.annotation;

import org.mapstruct.Mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
@Mapping(target = "crew", expression = CrewAircraftMapModify.GET_CREW)
@Mapping(target = "aircraft", expression = CrewAircraftMapModify.GET_AIRCRAFT)
public @interface CrewAircraftMapModify {

    String GET_CREW = """
                            java(java.util.Optional.ofNullable(object.getCrewId())
                            .flatMap(crewRepository::findById)
                            .orElse(null))
            """;

    String GET_AIRCRAFT = """
                            java(java.util.Optional.ofNullable(object.getAircraftId())
                            .flatMap(aircraftRepository::findById)
                            .orElse(null))
            """;
}
