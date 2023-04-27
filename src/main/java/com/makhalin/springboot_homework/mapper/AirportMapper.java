package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.AircraftReadDto;
import com.makhalin.springboot_homework.dto.AirportCreateEditDto;
import com.makhalin.springboot_homework.entity.Airport;
import com.makhalin.springboot_homework.repository.CityRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = CityMapper.class)
public abstract class AirportMapper {

    @Autowired
    protected CityRepository cityRepository;

    private static final String GET_CITY = """
                            java(java.util.Optional.ofNullable(object.getCityId())
                            .flatMap(cityRepository::findById)
                            .orElse(null))
            """;

    public abstract AircraftReadDto mapRead(Airport object);

    @Mapping(target = "city", expression = GET_CITY)
    public abstract Airport mapCreate(AirportCreateEditDto object);

    @Mapping(target = "city", expression = GET_CITY)
    public abstract Airport mapUpdate(AirportCreateEditDto object, @MappingTarget Airport toObject);
}
