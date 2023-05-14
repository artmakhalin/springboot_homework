package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.AirportCreateEditDto;
import com.makhalin.springboot_homework.dto.AirportReadDto;
import com.makhalin.springboot_homework.entity.Airport;
import com.makhalin.springboot_homework.mapper.annotation.AirportMapModify;
import com.makhalin.springboot_homework.repository.CityRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = CityMapper.class)
public abstract class AirportMapper {

    @Autowired
    protected CityRepository cityRepository;

    public abstract AirportReadDto mapRead(Airport object);

    @AirportMapModify
    public abstract Airport mapCreate(AirportCreateEditDto object);

    @AirportMapModify
    public abstract Airport mapUpdate(AirportCreateEditDto object, @MappingTarget Airport toObject);
}
