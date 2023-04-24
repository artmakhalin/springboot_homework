package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CrewCreateEditDto;
import com.makhalin.springboot_homework.dto.CrewReadDto;
import com.makhalin.springboot_homework.entity.Crew;
import com.makhalin.springboot_homework.mapper.annotation.PersonalInfoMapModify;
import com.makhalin.springboot_homework.mapper.annotation.PersonalInfoMapRead;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class CrewMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @PersonalInfoMapRead
    public abstract CrewReadDto mapRead(Crew object);

    @PersonalInfoMapModify
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(object.getRawPassword()))")
    public abstract Crew mapCreate(CrewCreateEditDto object);

    @PersonalInfoMapModify
    public abstract Crew mapUpdate(CrewCreateEditDto object, @MappingTarget Crew toObject);
}
