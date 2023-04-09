package com.makhalin.springboot_homework.integration.service;

import com.makhalin.springboot_homework.dto.CityCreateEditDto;
import com.makhalin.springboot_homework.dto.CityFilter;
import com.makhalin.springboot_homework.dto.CityReadDto;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.service.CityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CityServiceIT extends IntegrationTestBase {

    @Autowired
    private CityService cityService;

    @Test
    void findAllByCityFilter() {
        var filter = CityFilter.builder()
                               .country("usa")
                               .build();

        var actualResult = cityService.findAll(filter, PageRequest.of(0, 20));
        var cityNames = actualResult.get()
                                  .map(CityReadDto::getName)
                                  .toList();

        assertAll(
                () -> assertThat(actualResult.getTotalElements()).isEqualTo(2L),
                () -> assertThat(cityNames).containsExactlyInAnyOrder(
                        newYork.getName(),
                        seattle.getName()
                )
        );
    }

    @Test
    void findAll() {
        var actualResult = cityService.findAll();
        var cityNames = actualResult.stream()
                                    .map(CityReadDto::getName)
                                    .toList();

        assertAll(
                () -> assertThat(actualResult).hasSize(7),
                () -> assertThat(cityNames).containsExactlyInAnyOrder(
                        newYork.getName(),
                        seattle.getName(),
                        moscow.getName(),
                        stPetersburg.getName(),
                        volgograd.getName(),
                        paris.getName(),
                        lion.getName()
                )
        );
    }

    @Test
    void findById() {
        var actualResult = cityService.findById(seattle.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()
                               .getName()).isEqualTo(seattle.getName());
    }

    @Test
    void findAllByCountryId() {
        var actualResult = cityService.findAllByCountryId(russia.getId());
        var cityNames = actualResult.stream()
                                    .map(CityReadDto::getName);

        assertAll(
                () -> assertThat(actualResult).hasSize(3),
                () -> assertThat(cityNames).containsExactlyInAnyOrder(
                        moscow.getName(),
                        stPetersburg.getName(),
                        volgograd.getName()
                )
        );
    }

    @Test
    void create() {
        var cityDto = new CityCreateEditDto("Miami", usa.getId());

        var actualResult = cityService.create(cityDto);

        assertThat(actualResult.getId()).isNotNull();
    }

    @Test
    void update() {
        var cityDto = new CityCreateEditDto("Marcelle", france.getId());

        var actualResult = cityService.update(volgograd.getId(), cityDto);

        assertThat(actualResult).isPresent();
        assertAll(
                () -> assertThat(actualResult.get()
                                             .getName()).isEqualTo(cityDto.getName()),
                () -> assertThat(actualResult.get()
                                             .getCountry()
                                             .getId()).isEqualTo(cityDto.getCountryId())
        );
    }

    @Test
    void delete() {
        assertAll(
                () -> assertThat(cityService.delete(lion.getId())).isTrue(),
                () -> assertThat(cityService.delete(555)).isFalse()
        );
    }
}