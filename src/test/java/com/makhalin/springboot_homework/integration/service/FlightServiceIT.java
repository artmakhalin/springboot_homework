package com.makhalin.springboot_homework.integration.service;

import com.makhalin.springboot_homework.dto.FlightCreateEditDto;
import com.makhalin.springboot_homework.dto.FlightFilter;
import com.makhalin.springboot_homework.dto.FlightReadDto;
import com.makhalin.springboot_homework.exception.BadRequestException;
import com.makhalin.springboot_homework.exception.NotFoundException;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.service.FlightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

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
