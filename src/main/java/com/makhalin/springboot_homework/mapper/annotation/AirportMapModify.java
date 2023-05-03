package com.makhalin.springboot_homework.mapper.annotation;

import org.mapstruct.Mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
@Mapping(target = "city", expression = AirportMapModify.GET_CITY)
public @interface AirportMapModify {

    String GET_CITY = """
                            java(java.util.Optional.ofNullable(object.getCityId())
                            .flatMap(cityRepository::findById)
                            .orElse(null))
            """;
}
