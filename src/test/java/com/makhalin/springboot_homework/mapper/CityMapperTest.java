package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CityCreateEditDto;
import com.makhalin.springboot_homework.dto.CityReadDto;
import com.makhalin.springboot_homework.dto.CountryReadDto;
import com.makhalin.springboot_homework.entity.City;
import com.makhalin.springboot_homework.entity.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CityMapperTest {

    @Autowired
    private CityMapper cityMapper;

    private City seattle;
    private CityReadDto seattleDto;

    @BeforeEach
    void prepareObjects() {
        Country usa = Country.builder()
                             .name("USA")
                             .id(1)
                             .build();
        seattle = City.builder()
                      .name("Seattle")
                      .id(5)
                      .country(usa)
                      .build();
        var usaDto = new CountryReadDto(1, "USA");
        seattleDto = new CityReadDto(5, "Seattle", usaDto);
    }

    @Test
    void mapRead() {
        var actualResult = cityMapper.mapRead(seattle);

        assertThat(actualResult).isEqualTo(seattleDto);
    }

    @Test
    void mapCreate() {
        var actualResult = cityMapper.mapCreate(new CityCreateEditDto("Seattle", 1));

        assertThat(actualResult).isEqualTo(seattle);
    }

    @Test
    void mapUpdate() {
        var actualResult = cityMapper.mapUpdate(new CityCreateEditDto("Miami", 1), seattle);

        assertThat(actualResult.getName()).isEqualTo("Miami");
    }
}