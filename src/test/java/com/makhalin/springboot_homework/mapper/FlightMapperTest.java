package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.*;
import com.makhalin.springboot_homework.repository.AircraftRepository;
import com.makhalin.springboot_homework.repository.AirportRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

class FlightMapperTest extends MapperTestBase {

    @InjectMocks
    private FlightMapper flightMapper = new FlightMapperImpl();

    @Mock
    private AirportRepository airportRepository;

    @Mock
    private AircraftRepository aircraftRepository;

    @Mock
    private AirportMapper airportMapper;

    @Mock
    private AircraftMapper aircraftMapper;

    @Test
    void mapRead() {
        doReturn(boeingDto).when(aircraftMapper)
                           .mapRead(boeing);
        doReturn(jfkDto).when(airportMapper)
                        .mapRead(jfk);
        doReturn(seaDto).when(airportMapper)
                        .mapRead(sea);

        var actualResult = flightMapper.mapRead(jfkSea);

        assertThat(actualResult).isEqualTo(jfkSeaDto);
        verify(aircraftMapper).mapRead(boeing);
        verify(airportMapper).mapRead(jfk);
        verify(airportMapper).mapRead(sea);
    }

    @Test
    void mapCreate() {
        doReturn(Optional.of(boeing)).when(aircraftRepository)
                                     .findById(boeing.getId());
        doReturn(Optional.of(jfk)).when(airportRepository)
                                  .findById(jfk.getId());
        doReturn(Optional.of(sea)).when(airportRepository)
                                  .findById(sea.getId());

        var actualResult = flightMapper.mapCreate(new FlightCreateEditDto(
                "D520",
                jfk.getId(),
                sea.getId(),
                null,
                LocalDate.of(2023, 3, 3),
                6,
                1,
                boeing.getId()
        ));

        assertThat(actualResult).isEqualTo(jfkSea);
        verify(aircraftRepository).findById(boeing.getId());
        verify(airportRepository).findById(jfk.getId());
        verify(airportRepository).findById(sea.getId());
    }

    @Test
    void mapUpdate() {
        doReturn(Optional.of(boeing)).when(aircraftRepository)
                                     .findById(boeing.getId());
        doReturn(Optional.of(jfk)).when(airportRepository)
                                  .findById(jfk.getId());
        doReturn(Optional.of(sea)).when(airportRepository)
                                  .findById(sea.getId());

        var actualResult = flightMapper.mapUpdate(new FlightCreateEditDto(
                        "D666",
                        jfk.getId(),
                        sea.getId(),
                        null,
                        LocalDate.of(2020, 1, 1),
                        5,
                        30,
                        boeing.getId()
                ),
                jfkSea);

        assertAll(
                () -> assertThat(actualResult.getFlightNo()).isEqualTo("D666"),
                () -> assertThat(actualResult.getDepartureDate()).isEqualTo(LocalDate.of(2020, 1, 1)),
                () -> assertThat(actualResult.getTime()).isEqualTo(19800L)
        );
        verify(aircraftRepository).findById(boeing.getId());
        verify(airportRepository).findById(jfk.getId());
        verify(airportRepository).findById(sea.getId());
    }
}
