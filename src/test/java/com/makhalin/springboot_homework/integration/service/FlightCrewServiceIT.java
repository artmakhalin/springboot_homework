package com.makhalin.springboot_homework.integration.service;

import com.makhalin.springboot_homework.dto.CrewReadDto;
import com.makhalin.springboot_homework.dto.FlightCrewCreateEditDto;
import com.makhalin.springboot_homework.dto.FlightCrewReadDto;
import com.makhalin.springboot_homework.entity.ClassOfService;
import com.makhalin.springboot_homework.exception.BadRequestException;
import com.makhalin.springboot_homework.exception.NotFoundException;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.service.FlightCrewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class FlightCrewServiceIT extends IntegrationTestBase {

    @Autowired
    private FlightCrewService flightCrewService;

    @Test
    void findAll() {
        var actualResult = flightCrewService.findAll();

        assertThat(actualResult).hasSize(6);
    }

    @Test
    void findAllByFlightId() {
        var actualResult = flightCrewService.findAllByFlightId(svoVog.getId());
        var crewEmails = actualResult.stream()
                                     .map(FlightCrewReadDto::getCrew)
                                     .map(CrewReadDto::getEmail)
                                     .toList();

        assertAll(
                () -> assertThat(actualResult).hasSize(2),
                () -> assertThat(crewEmails).containsExactlyInAnyOrder(
                        alex.getEmail(),
                        bob.getEmail()
                )
        );
    }

    @Test
    void findById() {
        var actualResult = flightCrewService.findById(ledVogAlex.getId());

        assertAll(
                () -> assertThat(actualResult.getCrew()
                                             .getEmail()).isEqualTo(ledVogAlex.getCrew()
                                                                              .getEmail()),
                () -> assertThat(actualResult.getFlight()
                                             .getId()).isEqualTo(ledVogAlex.getFlight()
                                                                           .getId())
        );
    }

    @Test
    void shouldThrowNotFoundExceptionIfNoAssignmentById() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> flightCrewService.findById(555L));
    }

    @Test
    void create() {
        var flightCrewDto = new FlightCrewCreateEditDto(
                svoJfk.getId(),
                bob.getId(),
                ClassOfService.ECONOMY,
                false,
                false
        );

        var actualResult = flightCrewService.create(flightCrewDto);

        assertThat(actualResult.getId()).isNotNull();
    }

    @Test
    void shouldThrowBadRequestExceptionIfInvalidPermitDate() {
        var flightCrewDto = new FlightCrewCreateEditDto(
                jfkCdg.getId(),
                bob.getId(),
                ClassOfService.ECONOMY,
                false,
                false
        );

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> flightCrewService.create(flightCrewDto));
    }

    @Test
    void update() {
        var flightCrewDto = new FlightCrewCreateEditDto(
                ledVog.getId(),
                alex.getId(),
                ClassOfService.ECONOMY,
                false,
                true
        );

        var actualResult = flightCrewService.update(ledVogAlex.getId(), flightCrewDto);

        assertAll(
                () -> assertThat(actualResult.getClassOfService()).isEqualTo(flightCrewDto.getClassOfService()),
                () -> assertThat(actualResult.getIsPassenger()).isEqualTo(flightCrewDto.getIsPassenger()),
                () -> assertThat(actualResult.getIsTurnaround()).isEqualTo(flightCrewDto.getIsTurnaround())
        );
    }

    @Test
    void delete() {
        assertAll(
                () -> assertThatNoException()
                        .isThrownBy(() -> flightCrewService.delete(ledVogAlex.getId())),
                () -> assertThatExceptionOfType(NotFoundException.class)
                        .isThrownBy(() -> flightCrewService.delete(555L))
        );
    }
}
