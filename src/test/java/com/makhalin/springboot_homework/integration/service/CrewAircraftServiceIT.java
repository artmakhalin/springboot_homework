package com.makhalin.springboot_homework.integration.service;

import com.makhalin.springboot_homework.dto.CrewAircraftCreateEditDto;
import com.makhalin.springboot_homework.exception.BadRequestException;
import com.makhalin.springboot_homework.exception.NotFoundException;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.service.CrewAircraftService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class CrewAircraftServiceIT extends IntegrationTestBase {

    @Autowired
    private CrewAircraftService crewAircraftService;

    @Test
    void findAll() {
        var actualResult = crewAircraftService.findAll();

        assertThat(actualResult).hasSize(5);
    }

    @Test
    void findAllByCrewId() {
        var actualResult = crewAircraftService.findAllByCrewId(alex.getId());

        assertThat(actualResult).hasSize(2);
    }

    @Test
    void findById() {
        var actualResult = crewAircraftService.findById(alexAirbus320.getId());

        assertAll(
                () -> assertThat(actualResult.getCrew()
                                             .getEmail()).isEqualTo(alex.getEmail()),
                () -> assertThat(actualResult.getAircraft()
                                             .getModel()).isEqualTo(airbus320.getModel()),
                () -> assertThat(actualResult.getPermitDate()).isEqualTo(alexAirbus320.getPermitDate())
        );
    }

    @Test
    void shouldThrowNotFoundExceptionIfNoPermitById() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> crewAircraftService.findById(555L));
    }

    @Test
    void create() {
        var actualResult = crewAircraftService.create(new CrewAircraftCreateEditDto(
                LocalDate.of(2022, 1, 1),
                marta.getId(),
                airbus330.getId()
        ));

        assertThat(actualResult.getId()).isNotNull();
    }

    @Test
    void shouldThrowBadRequestExceptionWhenCreateIfInvalidPermitDate() {
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(
                        () -> crewAircraftService.create(
                                new CrewAircraftCreateEditDto(
                                        LocalDate.of(2020, 1, 1),
                                        jake.getId(),
                                        airbus330.getId()
                                )
                        )
                );
    }

    @Test
    void update() {
        var actualResult = crewAircraftService.update(
                alexBoeing777.getId(),
                new CrewAircraftCreateEditDto(
                        LocalDate.of(2022, 1, 1),
                        marta.getId(),
                        boeing777.getId()
                ));

        assertAll(
                () -> assertThat(actualResult.getCrew()
                                             .getEmail()).isEqualTo(marta.getEmail()),
                () -> assertThat(actualResult.getPermitDate()).isEqualTo(LocalDate.of(2022, 1, 1))
        );
    }

    @Test
    void shouldThrowBadRequestExceptionWhenUpdateIfInvalidPermitDate() {
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(
                        () -> crewAircraftService.update(
                                alexBoeing777.getId(),
                                new CrewAircraftCreateEditDto(
                                        LocalDate.of(2000, 1, 1),
                                        alex.getId(),
                                        boeing777.getId()
                                )
                        )
                );
    }

    @Test
    void delete() {
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> crewAircraftService.delete(bobBoeing777.getId())),
                () -> assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> crewAircraftService.delete(555L))
        );
    }
}
