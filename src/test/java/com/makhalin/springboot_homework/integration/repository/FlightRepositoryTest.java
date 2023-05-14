package com.makhalin.springboot_homework.integration.repository;

import com.makhalin.springboot_homework.dto.FlightFilter;
import com.makhalin.springboot_homework.entity.Flight;
import com.makhalin.springboot_homework.entity.FlightCrew;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.makhalin.springboot_homework.util.TestMocks.CrewAircraftMock.getAlexBoeing737;
import static com.makhalin.springboot_homework.util.TestMocks.FlightMock.getJfkSea;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class FlightRepositoryTest extends IntegrationTestBase {

    @Autowired
    private FlightRepository flightRepository;

    @Test
    void save() {
        var jfkEwr = getJfkEwr();
        flightRepository.save(jfkEwr);

        assertThat(jfkEwr.getId()).isNotNull();
    }

    @Test
    void delete() {
        flightRepository.delete(jfkCdg);
        var actualResult = flightRepository.findById(jfkCdg.getId());

        assertThat(actualResult).isEmpty();
    }

    @Test
    void update() {
        jfkCdg.setFlightNo("P222");
        jfkCdg.setTime(5555L);
        flightRepository.saveAndFlush(jfkCdg);

        var actualResult = flightRepository.findById(jfkCdg.getId());

        assertThat(actualResult).isPresent();
        assertAll(
                () -> assertThat(actualResult.get()
                                             .getFlightNo()).isEqualTo(jfkCdg.getFlightNo()),
                () -> assertThat(actualResult.get()
                                             .getTime()).isEqualTo(jfkCdg.getTime())
        );
    }

    @Test
    void findById() {
        var actualResult = flightRepository.findById(jfkCdg.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(jfkCdg);
    }

    @Test
    void findAll() {
        var actualResults = flightRepository.findAll();
        var flightNos = actualResults.stream()
                                     .map(Flight::getFlightNo)
                                     .toList();

        assertAll(
                () -> assertThat(actualResults).hasSize(6),
                () -> assertThat(flightNos).containsExactlyInAnyOrder(
                        svoJfk.getFlightNo(),
                        svoLed.getFlightNo(),
                        svoVog.getFlightNo(),
                        svoCdg.getFlightNo(),
                        ledVog.getFlightNo(),
                        jfkCdg.getFlightNo()
                )
        );
    }

    @Test
    void findByCrewAndMonth() {
        var jfkSea = getJfkSea();
        saveJfkSea(jfkSea);
        var filter = FlightFilter.builder()
                                 .crewEmail(alex.getEmail())
                                 .month(3)
                                 .year(2023)
                                 .build();
        var actualResult = flightRepository.findByCrewAndMonth(filter);
        var flightNos = actualResult.stream()
                                    .map(Flight::getFlightNo)
                                    .toList();
        var departureDates = actualResult.stream()
                                         .map(Flight::getDepartureDate)
                                         .toList();
        var months = departureDates.stream()
                                   .map(LocalDate::getMonthValue)
                                   .collect(toSet());
        var years = departureDates.stream()
                                  .map(LocalDate::getYear)
                                  .collect(toSet());
        Set<String> crewEmails = new HashSet<>();
        actualResult.stream()
                    .map(Flight::getFlightCrews)
                    .forEach(
                            flightCrews -> {
                                flightCrews.stream()
                                           .map(FlightCrew::getCrew)
                                           .forEach(
                                                   crew -> crewEmails.add(crew.getEmail())
                                           );
                            }
                    );

        assertAll(
                () -> assertThat(actualResult).hasSize(3),
                () -> assertThat(flightNos).containsExactlyInAnyOrder(
                        getJfkSea().getFlightNo(),
                        svoJfk.getFlightNo(),
                        svoLed.getFlightNo()
                ),
                () -> assertThat(months).hasSize(1),
                () -> assertThat(months).containsExactly(3),
                () -> assertThat(years).hasSize(1),
                () -> assertThat(years).containsExactly(2023),
                () -> assertThat(crewEmails).hasSize(1),
                () -> assertThat(crewEmails).containsExactly(alex.getEmail())
        );
    }

    @Test
    void findMonthlyFlightTimeStatisticsByCrewAndYear() {
        saveJfkSea(getJfkSea());
        var filter = FlightFilter.builder()
                                 .crewEmail(alex.getEmail())
                                 .year(2023)
                                 .build();
        var actualResult = flightRepository.findMonthlyFlightTimeStatisticsByCrewAndYear(filter);
        var monthTimes = actualResult.values()
                                     .stream()
                                     .toList();
        var months = actualResult.keySet();

        assertAll(
                () -> assertThat(actualResult).hasSize(3),
                () -> assertThat(monthTimes).containsExactly(16924L, 7920L, 60300L),
                () -> assertThat(months).containsExactly(1, 2, 3)
        );
    }

    @Test
    void findAircraftFlightTimeStatisticsByCrew() {
        saveJfkSea(getJfkSea());

        var actualResult = flightRepository.findAircraftFlightTimeStatisticsByCrew(alex.getEmail());
        var aircraftTimes = actualResult.values()
                                        .stream()
                                        .toList();
        var aircraft = actualResult.keySet();

        assertAll(
                () -> assertThat(actualResult).hasSize(3),
                () -> assertThat(aircraftTimes).containsExactlyInAnyOrder(21600L, 30600L, 32944L),
                () -> assertThat(aircraft).containsExactlyInAnyOrder(
                        boeing737.getModel(),
                        boeing777.getModel(),
                        airbus320.getModel()
                )
        );
    }

    private void saveJfkSea(Flight jfkSea) {
        var alexBoeing737 = getAlexBoeing737();
        alexBoeing737.setCrew(alex);
        alexBoeing737.setAircraft(boeing737);
        entityManager.persist(alexBoeing737);
        jfkSea.setDepartureAirport(jfk);
        jfkSea.setArrivalAirport(sea);
        jfkSea.setAircraft(boeing737);
        entityManager.persist(jfkSea);
        var jfkSeaAlex = getJfkSeaAlex();
        jfkSeaAlex.setFlight(jfkSea);
        entityManager.persist(jfkSeaAlex);
    }

    private Flight getJfkEwr() {
        return Flight.builder()
                     .flightNo("OL255")
                     .departureAirport(jfk)
                     .arrivalAirport(ewr)
                     .departureDate(LocalDate.of(2019, 10, 1))
                     .aircraft(airbus330)
                     .time(5474L)
                     .build();
    }
}