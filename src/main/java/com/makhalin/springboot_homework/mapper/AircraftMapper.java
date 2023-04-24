package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.AircraftCreateEditDto;
import com.makhalin.springboot_homework.dto.AircraftReadDto;
import com.makhalin.springboot_homework.entity.Aircraft;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AircraftMapper {

    AircraftReadDto mapRead(Aircraft object);

    Aircraft mapCreate(AircraftCreateEditDto object);

    Aircraft mapUpdate(AircraftCreateEditDto fromObject, @MappingTarget Aircraft toObject);
}
