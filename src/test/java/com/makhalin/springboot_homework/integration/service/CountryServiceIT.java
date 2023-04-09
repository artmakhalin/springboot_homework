package com.makhalin.springboot_homework.integration.service;

import com.makhalin.springboot_homework.dto.CountryCreateEditDto;
import com.makhalin.springboot_homework.dto.CountryReadDto;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.service.CountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CountryServiceIT extends IntegrationTestBase {

    @Autowired
    private CountryService countryService;

    @Test
    void findAll() {
        var actualResult = countryService.findAll();
        var countryNames = actualResult.stream()
                                       .map(CountryReadDto::getName)
                                       .toList();

        assertAll(
                () -> assertThat(actualResult).hasSize(4),
                () -> assertThat(countryNames).containsExactlyInAnyOrder(
                        russia.getName(),
                        usa.getName(),
                        france.getName(),
                        uk.getName()
                )
        );
    }

    @Test
    void findById() {
        var actualResult = countryService.findById(usa.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()
                               .getName()).isEqualTo(usa.getName());
    }

    @Test
    void create() {
        var countryDto = new CountryCreateEditDto("Spain");

        var actualResult = countryService.create(countryDto);

        assertThat(actualResult.getId()).isNotNull();
    }

    @Test
    void update() {
        var countryDto = new CountryCreateEditDto("Canada");

        var actualResult = countryService.update(usa.getId(), countryDto);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()
                               .getName()).isEqualTo(countryDto.getName());
    }

    @Test
    void delete() {
        assertAll(
                () -> assertThat(countryService.delete(uk.getId())).isTrue(),
                () -> assertThat(countryService.delete(555)).isFalse()
        );
    }
}