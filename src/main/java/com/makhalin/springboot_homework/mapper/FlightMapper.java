package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.FlightCreateEditDto;
import com.makhalin.springboot_homework.dto.FlightReadDto;
import com.makhalin.springboot_homework.entity.Flight;
import com.makhalin.springboot_homework.mapper.annotation.FlightMapModify;
import com.makhalin.springboot_homework.mapper.annotation.FlightMapRead;
import com.makhalin.springboot_homework.repository.AircraftRepository;
import com.makhalin.springboot_homework.repository.AirportRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {AirportMapper.class, AircraftMapper.class})
public abstract class FlightMapper {

    @Autowired
    protected AirportRepository airportRepository;

    @Autowired
    protected AircraftRepository aircraftRepository;

    @FlightMapRead
    public abstract FlightReadDto mapRead(Flight object);

    @FlightMapModify
    public abstract Flight mapCreate(FlightCreateEditDto object);

    @FlightMapModify
    public abstract Flight mapUpdate(FlightCreateEditDto object, @MappingTarget Flight toObject);
}
