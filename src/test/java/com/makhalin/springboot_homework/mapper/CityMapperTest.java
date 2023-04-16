package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CityCreateEditDto;
import com.makhalin.springboot_homework.dto.CityReadDto;
import com.makhalin.springboot_homework.dto.CountryReadDto;
import com.makhalin.springboot_homework.entity.City;
import com.makhalin.springboot_homework.entity.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CityMapperTest {

    @Autowired
    private CityMapper cityMapper;

    @Test
    void mapRead() {
        var usa = Country.builder()
                         .name("USA")
                         .id(1)
                         .build();
        var usaDto = new CountryReadDto(1, "USA");
        var seattle = City.builder()
                          .name("Seattle")
                          .id(5)
                          .country(usa)
                          .build();

        var actualResult = cityMapper.mapRead(seattle);
        var expectedResult = new CityReadDto(5, "Seattle", usaDto);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void mapCreate() {
        var usa = Country.builder()
                         .name("USA")
                         .id(1)
                         .build();
        var dto = new CityCreateEditDto("Seattle", 1);

        var actualResult = cityMapper.mapCreate(dto);
        var expectedResult = City.builder()
                                 .name("Seattle")
                                 .country(usa)
                                 .build();

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void mapUpdate() {
        var usa = Country.builder()
                         .name("USA")
                         .id(1)
                         .build();
        var seattle = City.builder()
                          .name("Seattle")
                          .id(5)
                          .country(usa)
                          .build();
        var dto = new CityCreateEditDto("Miami", 1);

        var actualResult = cityMapper.mapUpdate(dto, seattle);

        assertThat(actualResult.getName()).isEqualTo(dto.getName());
    }
}