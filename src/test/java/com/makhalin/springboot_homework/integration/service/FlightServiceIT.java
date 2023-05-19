package com.makhalin.springboot_homework.integration.service;

import com.makhalin.springboot_homework.dto.*;
import com.makhalin.springboot_homework.exception.BadRequestException;
import com.makhalin.springboot_homework.exception.NotFoundException;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.service.FlightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class FlightServiceIT extends IntegrationTestBase {

    @Autowired
    private FlightService flightService;

    @Test
    void findAllByFilter() {
        var filter = FlightFilter.builder()
                                 .departureAirport(svo.getCode())
                                 .build();

        var actualResult = flightService.findAll(filter, PageRequest.of(0, 20));
        var flightNos = actualResult.get()
                                    .map(FlightReadDto::getFlightNo)
                                    .toList();

        assertAll(
                () -> assertThat(actualResult.getTotalElements()).isEqualTo(4L),
                () -> assertThat(flightNos).containsExactlyInAnyOrder(
                        svoJfk.getFlightNo(),
                        svoLed.getFlightNo(),
                        svoVog.getFlightNo(),
                        svoCdg.getFlightNo()
                )
        );
    }

    @Test
    void findAllByFilterOnlyYear() {
        var filter = FlightFilter.builder()
                                 .year(2018)
                                 .build();

        var actualResult = flightService.findAll(filter, PageRequest.of(0, 20));
        var flightNos = actualResult.get()
                                    .map(FlightReadDto::getFlightNo)
                                    .toList();

        assertAll(
                () -> assertThat(actualResult.getTotalElements()).isEqualTo(1L),
                () -> assertThat(flightNos).containsExactlyInAnyOrder(jfkCdg.getFlightNo())
        );
    }

    @Test
    void findAllByFilterYearAndMonth() {
        var filter = FlightFilter.builder()
                                 .year(2023)
                                 .month(2)
                                 .build();

        var actualResult = flightService.findAll(filter, PageRequest.of(0, 20));
        var flightNos = actualResult.get()
                                    .map(FlightReadDto::getFlightNo)
                                    .toList();

        assertAll(
                () -> assertThat(actualResult.getTotalElements()).isEqualTo(1L),
                () -> assertThat(flightNos).containsExactlyInAnyOrder(ledVog.getFlightNo())
        );
    }

    @Test
    void findAllByCrewAndMonth() {
        var filter = FlightFilter.builder()
                                 .crewEmail(alex.getEmail())
                                 .month(3)
                                 .year(2023)
                                 .build();

        var actualResult = flightService.findAllByCrewAndMonth(filter);
        var flightNos = actualResult.getFlights()
                                    .stream()
                                    .map(FlightReadDto::getFlightNo)
                                    .toList();

        assertAll(
                () -> assertThat(actualResult.getFlights()).hasSize(2),
                () -> assertThat(flightNos).containsExactlyInAnyOrder(
                        svoJfk.getFlightNo(),
                        svoLed.getFlightNo()
                ),
                () -> assertThat(actualResult.getFlightTime()
                                             .getHours()).isEqualTo(10),
                () -> assertThat(actualResult.getFlightTime()
                                             .getMinutes()).isEqualTo(45)
        );
    }

    @Test
    void findStatisticsByCrewAndYear() {
        var filter = FlightFilter.builder()
                                 .crewEmail(alex.getEmail())
                                 .year(2023)
                                 .build();

        var actualResult = flightService.findStatisticsByCrewAndYear(filter);
        var monthTimes = actualResult.getStatistics()
                                     .values()
                                     .stream()
                                     .toList();
        var months = actualResult.getStatistics()
                                   .keySet();

        assertAll(
                () -> assertThat(actualResult.getStatistics()).hasSize(3),
                () -> assertThat(monthTimes).containsExactlyInAnyOrder(
                        new FlightTimeReadDto(4, 42),
                        new FlightTimeReadDto(2, 12),
                        new FlightTimeReadDto(10, 45)
                ),
                () -> assertThat(months).containsExactlyInAnyOrder(1, 2, 3),
                () -> assertThat(actualResult.getTotalTime()).isEqualTo(new FlightTimeReadDto(17, 39))
        );
    }

    @Test
    void shouldThrowBadRequestExceptionIfInvalidFilter() {
        var filter = FlightFilter.builder()
                                 .month(5)
                                 .build();

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> flightService.findAll(filter, PageRequest.of(0, 20)));
    }

    @Test
    void findAll() {
        var actualResult = flightService.findAll();
        var flightNos = actualResult.stream()
                                    .map(FlightReadDto::getFlightNo)
                                    .toList();

        assertAll(
                () -> assertThat(actualResult).hasSize(6),
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
    void findById() {
        var actualResult = flightService.findById(svoJfk.getId());

        assertAll(
                () -> assertThat(actualResult.getFlightNo()).isEqualTo(svoJfk.getFlightNo()),
                () -> assertThat(actualResult.getArrivalAirport()
                                             .getCode()).isEqualTo(svoJfk.getArrivalAirport()
                                                                         .getCode()),
                () -> assertThat(actualResult.getDepartureAirport()
                                             .getCode()).isEqualTo(svoJfk.getDepartureAirport()
                                                                         .getCode()),
                () -> assertThat(actualResult.getDepartureDate()).isEqualTo(svoJfk.getDepartureDate())
        );
    }

    @Test
    void shouldThrowNotFoundExceptionIfNoFlightById() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> flightService.findById(555L));
    }

    @Test
    void create() {
        var flightDto = new FlightCreateEditDto(
                "SU555",
                svo.getId(),
                vog.getId(),
                null,
                LocalDate.of(2022, 5, 5),
                3,
                15,
                airbus320.getId()
        );

        var actualResult = flightService.create(flightDto);

        assertThat(actualResult.getId()).isNotNull();
    }

    @Test
    void update() {
        var flightDto = new FlightCreateEditDto(
                "SU555",
                svo.getId(),
                vog.getId(),
                null,
                LocalDate.of(2022, 5, 5),
                3,
                15,
                airbus320.getId()
        );

        var actualResult = flightService.update(svoJfk.getId(), flightDto);

        assertAll(
                () -> assertThat(actualResult.getFlightNo()).isEqualTo(flightDto.getFlightNo()),
                () -> assertThat(actualResult.getDepartureAirport()
                                             .getCode()).isEqualTo(svo.getCode()),
                () -> assertThat(actualResult.getArrivalAirport()
                                             .getCode()).isEqualTo(vog.getCode()),
                () -> assertThat(actualResult.getDepartureDate()).isEqualTo(flightDto.getDepartureDate()),
                () -> assertThat(actualResult.getHours()).isEqualTo(flightDto.getHours()),
                () -> assertThat(actualResult.getMinutes()).isEqualTo(flightDto.getMinutes()),
                () -> assertThat(actualResult.getAircraft()
                                             .getId()).isEqualTo(flightDto.getAircraftId())
        );
    }

    @Test
    void delete() {
        assertAll(
                () -> assertThatNoException()
                        .isThrownBy(() -> flightService.delete(jfkCdg.getId())),
                () -> assertThatExceptionOfType(NotFoundException.class)
                        .isThrownBy(() -> flightService.delete(555L))
        );
    }
}
