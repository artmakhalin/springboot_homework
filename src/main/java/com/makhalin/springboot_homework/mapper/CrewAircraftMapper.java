package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CrewAircraftCreateEditDto;
import com.makhalin.springboot_homework.dto.CrewAircraftReadDto;
import com.makhalin.springboot_homework.entity.CrewAircraft;
import com.makhalin.springboot_homework.mapper.annotation.CrewAircraftMapModify;
import com.makhalin.springboot_homework.repository.AircraftRepository;
import com.makhalin.springboot_homework.repository.CrewRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {CrewMapper.class, AircraftMapper.class})
public abstract class CrewAircraftMapper {

    @Autowired
    protected CrewRepository crewRepository;

    @Autowired
    protected AircraftRepository aircraftRepository;

    public abstract CrewAircraftReadDto mapRead(CrewAircraft object);

    @CrewAircraftMapModify
    public abstract CrewAircraft mapCreate(CrewAircraftCreateEditDto object);

    @CrewAircraftMapModify
    public abstract CrewAircraft mapUpdate(CrewAircraftCreateEditDto object, @MappingTarget CrewAircraft toObject);
}
