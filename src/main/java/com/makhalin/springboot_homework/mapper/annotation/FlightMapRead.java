package com.makhalin.springboot_homework.mapper.annotation;

import org.mapstruct.Mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
@Mapping(target = "hours", expression = "java(com.makhalin.springboot_homework.util.FlightTimeUtil.hoursFromSec(object.getTime()))")
@Mapping(target = "minutes", expression = "java(com.makhalin.springboot_homework.util.FlightTimeUtil.minFromSec(object.getTime()))")
public @interface FlightMapRead {
}
