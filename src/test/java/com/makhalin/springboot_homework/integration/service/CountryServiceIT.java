package com.makhalin.springboot_homework.integration.service;

import com.makhalin.springboot_homework.dto.CountryCreateEditDto;
import com.makhalin.springboot_homework.dto.CountryReadDto;
import com.makhalin.springboot_homework.exception.NotFoundException;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.service.CountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
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

        assertThat(actualResult.getName()).isEqualTo(usa.getName());
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

        assertThat(actualResult.getName()).isEqualTo(countryDto.getName());
    }

    @Test
    void delete() {
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> countryService.delete(uk.getId())),
                () -> assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> countryService.delete(555))
        );
    }
}