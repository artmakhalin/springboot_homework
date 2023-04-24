package com.makhalin.springboot_homework.integration.service;

import com.makhalin.springboot_homework.dto.AircraftCreateEditDto;
import com.makhalin.springboot_homework.dto.AircraftReadDto;
import com.makhalin.springboot_homework.exception.NotFoundException;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.service.AircraftService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class AircraftServiceIT extends IntegrationTestBase {

    @Autowired
    private AircraftService aircraftService;

    @Test
    void findAll() {
        var actualResult = aircraftService.findAll();
        var aircraftModels = actualResult.stream()
                                         .map(AircraftReadDto::getModel)
                                         .toList();

        assertAll(
                () -> assertThat(actualResult).hasSize(4),
                () -> assertThat(aircraftModels).containsExactlyInAnyOrder(
                        airbus320.getModel(),
                        airbus330.getModel(),
                        boeing737.getModel(),
                        boeing777.getModel()
                )
        );
    }

    @Test
    void findById() {
        var actualResult = aircraftService.findById(airbus330.getId());

        assertThat(actualResult.getModel()).isEqualTo(airbus330.getModel());
    }

    @Test
    void shouldThrowNotFoundExceptionIfNoAircraftById() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> aircraftService.findById(555));
    }

    @Test
    void create() {
        var aircraftDto = new AircraftCreateEditDto("RRJ-95");

        var actualResult = aircraftService.create(aircraftDto);

        assertThat(actualResult.getId()).isNotNull();
    }

    @Test
    void update() {
        var aircraftDto = new AircraftCreateEditDto("RRJ-95");

        var actualResult = aircraftService.update(boeing737.getId(), aircraftDto);

        assertThat(actualResult.getModel()).isEqualTo(aircraftDto.getModel());
    }

    @Test
    void delete() {
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> aircraftService.delete(airbus330.getId())),
                () -> assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> aircraftService.delete(555))
        );
    }
}
