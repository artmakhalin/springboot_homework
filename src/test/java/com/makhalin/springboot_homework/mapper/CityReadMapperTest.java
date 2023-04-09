package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CityReadDto;
import com.makhalin.springboot_homework.dto.CountryReadDto;
import com.makhalin.springboot_homework.entity.City;
import com.makhalin.springboot_homework.entity.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CityReadMapperTest {

    @Mock
    private CountryReadMapper countryReadMapper;

    @InjectMocks
    private CityReadMapper cityReadMapper;

    @Test
    void map() {
        var usa = Country.builder()
                         .name("USA")
                         .id(1)
                         .build();
        var usaDto = new CountryReadDto(1, "USA");
        doReturn(usaDto)
                .when(countryReadMapper)
                .map(usa);
        var seattle = City.builder()
                          .name("Seattle")
                          .id(5)
                          .country(usa)
                          .build();

        var actualResult = cityReadMapper.map(seattle);
        var expectedResult = new CityReadDto(5, "Seattle", usaDto);

        assertThat(actualResult).isEqualTo(expectedResult);
        verify(countryReadMapper).map(usa);
    }
}