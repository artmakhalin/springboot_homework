package com.makhalin.springboot_homework.mapper.annotation;

import org.mapstruct.Mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
@Mapping(target = "personalInfo.firstname", source = "firstname")
@Mapping(target = "personalInfo.lastname", source = "lastname")
@Mapping(target = "personalInfo.birthDate", source = "birthDate")
public @interface PersonalInfoMapModify {
}
