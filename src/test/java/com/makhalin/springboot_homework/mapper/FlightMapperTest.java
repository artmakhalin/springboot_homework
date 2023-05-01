package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.*;
import com.makhalin.springboot_homework.entity.*;
import com.makhalin.springboot_homework.repository.AircraftRepository;
import com.makhalin.springboot_homework.repository.AirportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FlightMapperTest {

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

    private Aircraft boeing;
    private Airport jfk;
    private Airport sea;
    private AircraftReadDto boeingDto;
    private AirportReadDto jfkDto;
    private AirportReadDto seaDto;
    private Flight jfkSea;
    private FlightReadDto jfkSeaDto;

    @BeforeEach
    void prepareObjects() {
        boeing = Aircraft.builder()
                         .id(5)
                         .model("Boeing-777")
                         .build();
        boeingDto = new AircraftReadDto(5, "Boeing-777");
        var usa = Country.builder()
                         .name("USA")
                         .id(1)
                         .build();
        var newYork = City.builder()
                          .name("New York")
                          .id(1)
                          .country(usa)
                          .build();
        var seattle = City.builder()
                          .name("Seattle")
                          .id(2)
                          .country(usa)
                          .build();
        jfk = Airport.builder()
                     .code("JFK")
                     .id(1)
                     .city(newYork)
                     .build();
        sea = Airport.builder()
                     .code("SEA")
                     .id(2)
                     .city(seattle)
                     .build();
        var usaDto = new CountryReadDto(1, "USA");
        var newYorkDto = new CityReadDto(1, "New York", usaDto);
        var seattleDto = new CityReadDto(2, "Seattle", usaDto);
        jfkDto = new AirportReadDto(1, "JFK", newYorkDto);
        seaDto = new AirportReadDto(2, "SEA", seattleDto);
        jfkSea = Flight.builder()
                       .id(55L)
                       .flightNo("D520")
                       .departureDate(LocalDate.of(2023, 3, 3))
                       .time(21660L)
                       .arrivalAirport(jfk)
                       .departureAirport(sea)
                       .aircraft(boeing)
                       .build();
        jfkSeaDto = new FlightReadDto(
                55L,
                "D520",
                seaDto,
                jfkDto,
                null,
                LocalDate.of(2023, 3, 3),
                6,
                1,
                boeingDto
        );
    }

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
