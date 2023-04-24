package com.makhalin.springboot_homework.mapper.annotation;

import org.mapstruct.Mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
@Mapping(target = "firstname", source = "personalInfo.firstname")
@Mapping(target = "lastname", source = "personalInfo.lastname")
@Mapping(target = "birthDate", source = "personalInfo.birthDate")
public @interface PersonalInfoMapRead {
}
