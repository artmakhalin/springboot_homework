package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.AirportCreateEditDto;
import com.makhalin.springboot_homework.dto.AirportReadDto;
import com.makhalin.springboot_homework.dto.CityReadDto;
import com.makhalin.springboot_homework.dto.CountryReadDto;
import com.makhalin.springboot_homework.entity.Airport;
import com.makhalin.springboot_homework.entity.City;
import com.makhalin.springboot_homework.entity.Country;
import com.makhalin.springboot_homework.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AirportMapperTest {

    @InjectMocks
    private AirportMapper airportMapper = new AirportMapperImpl();

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CityMapper cityMapper;

    private Airport jfk;
    private AirportReadDto jfkDto;
    private City newYork;
    private CityReadDto newYorkDto;

    @BeforeEach
    void prepareObjects() {
        var usa = Country.builder()
                         .name("USA")
                         .id(1)
                         .build();
        newYork = City.builder()
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
        newYorkDto = new CityReadDto(1, "New York", usaDto);
        jfkDto = new AirportReadDto(1,"JFK", newYorkDto);
    }

    @Test
    void mapRead() {
        doReturn(newYorkDto).when(cityMapper).mapRead(newYork);
        var actualResult = airportMapper.mapRead(jfk);

        assertThat(actualResult).isEqualTo(jfkDto);
        verify(cityMapper).mapRead(newYork);
    }

    @Test
    void mapCreate() {
        doReturn(Optional.of(newYork)).when(cityRepository).findById(newYork.getId());
        var actualResult = airportMapper.mapCreate(new AirportCreateEditDto("JFK", 1));

        assertThat(actualResult).isEqualTo(jfk);
        verify(cityRepository).findById(newYork.getId());
    }

    @Test
    void mapUpdate() {
        doReturn(Optional.of(newYork)).when(cityRepository).findById(newYork.getId());
        var actualResult = airportMapper.mapUpdate(new AirportCreateEditDto("LGA", 1), jfk);

        assertThat(actualResult.getCode()).isEqualTo("LGA");
        verify(cityRepository).findById(newYork.getId());
    }
}
