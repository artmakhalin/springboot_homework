package com.makhalin.springboot_homework.mapper.annotation;

import org.mapstruct.Mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
@Mapping(target = "country", expression = CityMapModify.GET_COUNTRY)
public @interface CityMapModify {

    String GET_COUNTRY = """
                            java(java.util.Optional.ofNullable(object.getCountryId())
                            .flatMap(countryRepository::findById)
                            .orElse(null))
            """;
}
