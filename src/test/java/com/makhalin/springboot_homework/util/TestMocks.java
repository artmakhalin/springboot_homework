package com.makhalin.springboot_homework.util;

import com.makhalin.springboot_homework.entity.*;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class TestMocks {

    public class AircraftMock {
        public static Aircraft getBoeing737() {
            return Aircraft.builder()
                           .model("Boeing-737")
                           .build();
        }

        public static Aircraft getBoeing777() {
            return Aircraft.builder()
                           .model("Boeing-777")
                           .build();
        }

        public static Aircraft getAirbus320() {
            return Aircraft.builder()
                           .model("Airbus-320")
                           .build();
        }

        public static Aircraft getAirbus330() {
            return Aircraft.builder()
                           .model("Airbus-330")
                           .build();
        }
    }

    public class CountryMock {
        public static Country getUsa() {
            return Country.builder()
                          .name("USA")
                          .build();
        }

        public static Country getRussia() {
            return Country.builder()
                          .name("Russia")
                          .build();
        }

        public static Country getFrance() {
            return Country.builder()
                          .name("France")
                          .build();
        }

        public static Country getUk() {
            return Country.builder()
                          .name("UK")
                          .build();
        }
    }

    public class CityMock {
        public static City getNewYork() {
            return City.builder()
                       .name("New York")
                       .build();
        }

        public static City getSeattle() {
            return City.builder()
                       .name("Seattle")
                       .build();
        }

        public static City getMoscow() {
            return City.builder()
                       .name("Moscow")
                       .build();
        }

        public static City getStPetersburg() {
            return City.builder()
                       .name("St Petersburg")
                       .build();
        }

        public static City getVolgograd() {
            return City.builder()
                       .name("Volgograd")
                       .build();
        }

        public static City getParis() {
            return City.builder()
                       .name("Paris")
                       .build();
        }

        public static City getLion() {
            return City.builder()
                       .name("Lion")
                       .build();
        }
    }

    public class AirportMock {
        public static Airport getJfk() {
            return Airport.builder()
                          .code("JFK")
                          .build();
        }

        public static Airport getSea() {
            return Airport.builder()
                          .code("SEA")
                          .build();
        }


        public static Airport getSvo() {
            return Airport.builder()
                          .code("SVO")
                          .build();
        }

        public static Airport getLed() {
            return Airport.builder()
                          .code("LED")
                          .build();
        }

        public static Airport getVog() {
            return Airport.builder()
                          .code("VOG")
                          .build();
        }

        public static Airport getCdg() {
            return Airport.builder()
                          .code("CDG")
                          .build();
        }

        public static Airport getEwr() {
            return Airport.builder()
                          .code("EWR")
                          .build();
        }
    }

    public class CrewMock {
        public static Crew getAlex() {
            return Crew.builder()
                       .personalInfo(PersonalInfo.builder()
                                                 .firstname("Alex")
                                                 .lastname("Test")
                                                 .birthDate(LocalDate.of(1995, 1, 1))
                                                 .build())
                       .email("alex@test.com")
                       .password("test")
                       .employmentDate(LocalDate.of(2015, 12, 5))
                       .role(Role.USER)
                       .build();
        }

        public static Crew getJake() {
            return Crew.builder()
                       .personalInfo(PersonalInfo.builder()
                                                 .firstname("Jake")
                                                 .lastname("Test")
                                                 .birthDate(LocalDate.of(1995, 1, 1))
                                                 .build())
                       .email("jake@test.com")
                       .password("test")
                       .employmentDate(LocalDate.of(2020, 12, 5))
                       .role(Role.USER)
                       .build();
        }

        public static Crew getBob() {
            return Crew.builder()
                       .personalInfo(PersonalInfo.builder()
                                                 .firstname("Bob")
                                                 .lastname("Test")
                                                 .birthDate(LocalDate.of(1995, 1, 1))
                                                 .build())
                       .email("bob@test.com")
                       .password("test")
                       .employmentDate(LocalDate.of(2014, 12, 5))
                       .role(Role.USER)
                       .build();
        }

        public static Crew getMarta() {
            return Crew.builder()
                       .personalInfo(PersonalInfo.builder()
                                                 .firstname("Marta")
                                                 .lastname("Test")
                                                 .birthDate(LocalDate.of(1982, 1, 12))
                                                 .build())
                       .email("marta@test.com")
                       .password("test")
                       .employmentDate(LocalDate.of(1999, 12, 15))
                       .role(Role.USER)
                       .build();
        }
    }

    public class CrewAircraftMock {
        public static CrewAircraft getAlexBoeing737() {
            return CrewAircraft.builder()
                               .permitDate(LocalDate.of(2020, 1, 1))
                               .build();
        }

        public static CrewAircraft getAlexBoeing777() {
            return CrewAircraft.builder()
                               .permitDate(LocalDate.of(2021, 1, 1))
                               .build();
        }

        public static CrewAircraft getAlexAirbus320() {
            return CrewAircraft.builder()
                               .permitDate(LocalDate.of(2021, 1, 1))
                               .build();
        }

        public static CrewAircraft getJakeBoeing737() {
            return CrewAircraft.builder()
                               .permitDate(LocalDate.of(2021, 1, 1))
                               .build();
        }

        public static CrewAircraft getBobAirbus320() {
            return CrewAircraft.builder()
                               .permitDate(LocalDate.of(2021, 1, 1))
                               .build();
        }

        public static CrewAircraft getBobBoeing777() {
            return CrewAircraft.builder()
                               .permitDate(LocalDate.of(2021, 1, 1))
                               .build();
        }
    }

    public class FlightMock {
        public static Flight getJfkSea() {
            return Flight.builder()
                         .flightNo("D520")
                         .departureDate(LocalDate.of(2023, 3, 3))
                         .time(21600L)
                         .build();
        }

        public static Flight getSvoJfk() {
            return Flight.builder()
                         .flightNo("SU100")
                         .departureDate(LocalDate.of(2023, 3, 10))
                         .time(30600L)
                         .build();
        }

        public static Flight getSvoLed() {
            return Flight.builder()
                         .flightNo("SU25")
                         .departureDate(LocalDate.of(2023, 3, 15))
                         .time(8100L)
                         .build();
        }

        public static Flight getSvoVog() {
            return Flight.builder()
                         .flightNo("SU1188")
                         .departureDate(LocalDate.of(2023, 1, 18))
                         .time(5404L)
                         .build();
        }

        public static Flight getSvoCdg() {
            return Flight.builder()
                         .flightNo("SU2454")
                         .departureDate(LocalDate.of(2023, 1, 15))
                         .time(11520L)
                         .build();
        }

        public static Flight getLedVog() {
            return Flight.builder()
                         .flightNo("DP612")
                         .departureDate(LocalDate.of(2023, 2, 19))
                         .time(7920L)
                         .build();
        }

        public static Flight getJfkCdg() {
            return Flight.builder()
                         .flightNo("DL1285")
                         .departureDate(LocalDate.of(2018, 3, 19))
                         .time(11835L)
                         .build();
        }
    }
}
