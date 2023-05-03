package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.FlightCrewCreateEditDto;
import com.makhalin.springboot_homework.dto.FlightCrewReadDto;
import com.makhalin.springboot_homework.entity.FlightCrew;
import com.makhalin.springboot_homework.mapper.annotation.FlightCrewMapModify;
import com.makhalin.springboot_homework.repository.CrewRepository;
import com.makhalin.springboot_homework.repository.FlightRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {FlightMapper.class, CrewMapper.class})
public abstract class FlightCrewMapper {

    @Autowired
    protected FlightRepository flightRepository;

    @Autowired
    protected CrewRepository crewRepository;

    public abstract FlightCrewReadDto mapRead(FlightCrew object);

    @FlightCrewMapModify
    public abstract FlightCrew mapCreate(FlightCrewCreateEditDto object);

    @FlightCrewMapModify
    public abstract FlightCrew mapUpdate(FlightCrewCreateEditDto object, @MappingTarget FlightCrew toObject);
}
