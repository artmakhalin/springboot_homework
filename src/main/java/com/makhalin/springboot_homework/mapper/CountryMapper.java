package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CountryCreateEditDto;
import com.makhalin.springboot_homework.dto.CountryReadDto;
import com.makhalin.springboot_homework.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryReadDto mapRead(Country object);

    Country mapCreate(CountryCreateEditDto object);

    Country mapUpdate(CountryCreateEditDto fromObject, @MappingTarget Country toObject);
}
