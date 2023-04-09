package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CountryReadDto;
import com.makhalin.springboot_homework.entity.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CountryReadMapperTest {

    @InjectMocks
    private CountryReadMapper countryReadMapper;

    @Test
    void map() {
        var usa = Country.builder()
                         .id(1)
                         .name("USA")
                         .build();

        var actualResult = countryReadMapper.map(usa);
        var expectedResult = new CountryReadDto(1, "USA");

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}