package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CityReadDto;
import com.makhalin.springboot_homework.entity.City;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CityReadMapper implements Mapper<City, CityReadDto> {

    private final CountryReadMapper countryReadMapper;

    @Override
    public CityReadDto map(City object) {
        var country = Optional.ofNullable(object.getCountry())
                                     .map(countryReadMapper::map)
                                     .orElse(null);

        return new CityReadDto(
                object.getId(),
                object.getName(),
                country
        );
    }
}
