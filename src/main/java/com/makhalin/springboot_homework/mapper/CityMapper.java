package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CityCreateEditDto;
import com.makhalin.springboot_homework.dto.CityReadDto;
import com.makhalin.springboot_homework.entity.City;
import com.makhalin.springboot_homework.repository.CountryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = CountryMapper.class)
public abstract class CityMapper {

    @Autowired
    protected CountryRepository countryRepository;

    private static final String GET_COUNTRY = """
                            java(java.util.Optional.ofNullable(object.getCountryId())
                            .flatMap(countryRepository::findById)
                            .orElse(null))
            """;

    public abstract CityReadDto mapRead(City object);

    @Mapping(target = "country", expression = GET_COUNTRY)
    public abstract City mapCreate(CityCreateEditDto object);

    @Mapping(target = "country", expression = GET_COUNTRY)
    public abstract City mapUpdate(CityCreateEditDto object, @MappingTarget City toObject);
}
