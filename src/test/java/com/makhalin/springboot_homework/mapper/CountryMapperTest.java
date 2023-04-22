package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CountryCreateEditDto;
import com.makhalin.springboot_homework.dto.CountryReadDto;
import com.makhalin.springboot_homework.entity.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CountryMapperTest {

    @Autowired
    private CountryMapper countryMapper;

    @Test
    void mapRead() {
        var usa = getCountry();

        var actualResult = countryMapper.mapRead(usa);
        var expectedResult = new CountryReadDto(1, "USA");

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void mapCreate() {
        var dto = new CountryCreateEditDto("Spain");

        var actualResult = countryMapper.mapCreate(dto);

        assertThat(actualResult.getName()).isEqualTo(dto.getName());
    }

    @Test
    void mapUpdate() {
        var usa = getCountry();
        var dto = new CountryCreateEditDto("Spain");

        var actualResult = countryMapper.mapUpdate(dto, usa);

        assertThat(actualResult.getName()).isEqualTo(dto.getName());
    }

    private Country getCountry() {
        return Country.builder()
                      .id(1)
                      .name("USA")
                      .build();
    }
}