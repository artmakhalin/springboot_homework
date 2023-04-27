package com.makhalin.springboot_homework.integration.service;

import com.makhalin.springboot_homework.dto.AirportCreateEditDto;
import com.makhalin.springboot_homework.dto.AirportFilter;
import com.makhalin.springboot_homework.dto.AirportReadDto;
import com.makhalin.springboot_homework.exception.NotFoundException;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.service.AirportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AirportServiceIT extends IntegrationTestBase {

    @Autowired
    private AirportService airportService;

    @Test
    void findAll() {
        var filter = AirportFilter.builder()
                                  .country("usa")
                                  .build();

        var actualResult = airportService.findAll(filter, PageRequest.of(0, 20));
        var airports = actualResult.get()
                                   .map(AirportReadDto::getCode)
                                   .toList();

        assertAll(
                () -> assertThat(actualResult.getTotalElements()).isEqualTo(3L),
                () -> assertThat(airports).containsExactlyInAnyOrder(
                        jfk.getCode(),
                        sea.getCode(),
                        ewr.getCode()
                )
        );
    }

    @Test
    void findByCode() {
        var actualResult = airportService.findByCode(jfk.getCode());

        assertThat(actualResult.getCode()).isEqualTo(jfk.getCode());
    }

    @Test
    void shouldThrowNotFoundExceptionIfNoAirportByCode() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> airportService.findByCode("XXX"));
    }

    @Test
    void findAllByCityId() {
        var actualResult = airportService.findAllByCityId(newYork.getId());
        var airports = actualResult.stream()
                                   .map(AirportReadDto::getCode)
                                   .toList();

        assertAll(
                () -> assertThat(actualResult).hasSize(2),
                () -> assertThat(airports).containsExactlyInAnyOrder(
                        jfk.getCode(),
                        ewr.getCode()
                )
        );
    }

    @Test
    void create() {
        var airportDto = new AirportCreateEditDto("LGA", newYork.getId());

        var actualResult = airportService.create(airportDto);
        var expectedResult = airportService.findByCode("LGA");

        assertThat(actualResult).isEqualTo(expectedResult);
    }

//    @Test
//    void update() {
//        var airportDto = new AirportCreateEditDto("LGA", newYork.getId());
//
//        var actualResult = airportService.update("SEA", airportDto);
//
//        assertAll(
//                () -> assertThat(actualResult.getCode()).isEqualTo(airportDto.getCode()),
//                () -> assertThat(actualResult.getCity()
//                                             .getId()).isEqualTo(airportDto.getCityId())
//        );
//    }

    @Test
    void delete() {
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> airportService.delete(ewr.getCode())),
                () -> assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> airportService.delete("XXX"))
        );
    }
}
