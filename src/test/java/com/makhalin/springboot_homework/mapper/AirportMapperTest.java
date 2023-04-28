package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.AirportCreateEditDto;
import com.makhalin.springboot_homework.dto.AirportReadDto;
import com.makhalin.springboot_homework.dto.CityReadDto;
import com.makhalin.springboot_homework.dto.CountryReadDto;
import com.makhalin.springboot_homework.entity.Airport;
import com.makhalin.springboot_homework.entity.City;
import com.makhalin.springboot_homework.entity.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AirportMapperTest {

    @Autowired
    private AirportMapper airportMapper;

    private Airport jfk;
    private AirportReadDto jfkDto;

    @BeforeEach
    void prepareObjects() {
        var usa = Country.builder()
                         .name("USA")
                         .id(1)
                         .build();
        var newYork = City.builder()
                          .name("New York")
                          .id(1)
                          .country(usa)
                          .build();
        jfk = Airport.builder()
                     .code("JFK")
                     .id(1)
                     .city(newYork)
                     .build();
        var usaDto = new CountryReadDto(1, "USA");
        var newYorkDto = new CityReadDto(1, "New York", usaDto);
        jfkDto = new AirportReadDto(1,"JFK", newYorkDto);
    }

    @Test
    void mapRead() {
        var actualResult = airportMapper.mapRead(jfk);

        assertThat(actualResult).isEqualTo(jfkDto);
    }

    @Test
    void mapCreate() {
        var actualResult = airportMapper.mapCreate(new AirportCreateEditDto("JFK", 1));

        assertThat(actualResult).isEqualTo(jfk);
    }

    @Test
    void mapUpdate() {
        var actualResult = airportMapper.mapUpdate(new AirportCreateEditDto("LGA", 1), jfk);

        assertThat(actualResult.getCode()).isEqualTo("LGA");
    }
}
