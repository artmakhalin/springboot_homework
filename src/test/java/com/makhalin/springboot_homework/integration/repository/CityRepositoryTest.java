package com.makhalin.springboot_homework.integration.repository;

import com.makhalin.springboot_homework.entity.City;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CityRepositoryTest extends IntegrationTestBase {

    @Autowired
    private CityRepository cityRepository;

    @Test
    void save() {
        var london = getLondon();
        cityRepository.save(london);

        assertThat(london.getId()).isNotNull();
    }

    @Test
    void delete() {
        cityRepository.delete(lion);
        var actualResult = cityRepository.findById(lion.getId());

        assertThat(actualResult).isEmpty();
    }

    @Test
    void update() {
        lion.setName("Marsel");
        cityRepository.saveAndFlush(lion);

        var actualResult = cityRepository.findById(lion.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()
                               .getName()).isEqualTo(lion.getName());
    }

    @Test
    void findById() {
        var actualResult = cityRepository.findById(lion.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(lion);
    }

    @Test
    void findAll() {
        var actualResults = cityRepository.findAll();
        var cityNames = actualResults.stream()
                                     .map(City::getName)
                                     .toList();

        assertAll(
                () -> assertThat(actualResults).hasSize(7),
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
    void findAllByCrew() {
        var actualResult = cityRepository.findCityAndCountOfVisitsByCrew(alex.getEmail());
        var cityNames = actualResult.stream()
                                    .map(r -> Objects.requireNonNull(r.get(0, City.class))
                                                     .getName())
                                    .toList();
        var counts = actualResult.stream()
                                 .map(r -> r.get(1, Long.class))
                                 .toList();

        assertAll(
                () -> assertThat(actualResult).hasSize(4),
                () -> assertThat(cityNames).containsExactlyInAnyOrder(
                        newYork.getName(),
                        stPetersburg.getName(),
                        volgograd.getName(),
                        paris.getName()
                ),
                () -> assertThat(counts).contains(1L, 2L)
        );
    }

    @Test
    void findAllByCountryId() {
        var actualResult = cityRepository.findAllByCountryId(russia.getId());
        var cityNames = actualResult.stream()
                                    .map(City::getName);

        assertAll(
                () -> assertThat(actualResult).hasSize(3),
                () -> assertThat(cityNames).containsExactlyInAnyOrder(
                        moscow.getName(),
                        stPetersburg.getName(),
                        volgograd.getName()
                )
        );
    }

    private City getLondon() {
        return City.builder()
                   .name("London")
                   .country(uk)
                   .build();
    }
}