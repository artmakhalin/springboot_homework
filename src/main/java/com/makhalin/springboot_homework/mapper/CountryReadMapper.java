package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CountryReadDto;
import com.makhalin.springboot_homework.entity.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryReadMapper implements Mapper<Country, CountryReadDto> {

    @Override
    public CountryReadDto map(Country object) {
        return new CountryReadDto(
                object.getId(),
                object.getName()
        );
    }
}
