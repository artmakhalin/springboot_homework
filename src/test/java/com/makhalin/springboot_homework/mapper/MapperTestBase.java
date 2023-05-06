package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.*;
import com.makhalin.springboot_homework.entity.*;
import com.makhalin.springboot_homework.util.TestMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static com.makhalin.springboot_homework.util.TestMocks.CrewMock.getAlex;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public abstract class MapperTestBase {

    protected Aircraft boeing;
    protected Airport jfk;
    protected Airport sea;
    protected AircraftReadDto boeingDto;
    protected AirportReadDto jfkDto;
    protected AirportReadDto seaDto;
    protected Flight jfkSea;
    protected FlightReadDto jfkSeaDto;
    protected Crew alex;
    protected CrewReadDto alexDto;
    protected FlightCrew alexJfkSea;
    protected FlightCrewReadDto alexJfkSeaDto;

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
        alex = Crew.builder()
                   .id(1)
                   .email("alex@test.com")
                   .personalInfo(
                           PersonalInfo.builder()
                                       .firstname("Alex")
                                       .lastname("Test")
                                       .birthDate(LocalDate.of(1990, 1, 1))
                                       .build()
                   )
                   .employmentDate(LocalDate.of(2020, 1, 1))
                   .mkkDate(LocalDate.of(2023, 12, 4))
                   .role(Role.USER)
                   .build();
        alexDto = new CrewReadDto(
                1,
                "alex@test.com",
                "Alex",
                "Test",
                LocalDate.of(1990, 1, 1),
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2023, 12, 4),
                Role.USER
        );
        alexJfkSea = FlightCrew.builder()
                               .id(1L)
                               .crew(alex)
                               .flight(jfkSea)
                               .classOfService(ClassOfService.BUSINESS)
                               .isPassenger(true)
                               .isTurnaround(true)
                               .build();
        alexJfkSeaDto = new FlightCrewReadDto(
                1L,
                jfkSeaDto,
                alexDto,
                ClassOfService.BUSINESS,
                true,
                true
        );
    }
}
