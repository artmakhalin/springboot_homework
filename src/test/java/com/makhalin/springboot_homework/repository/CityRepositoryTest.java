package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.City;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CityRepositoryTest extends IntegrationTestBase {

    private final CityRepository cityRepository;

    public CityRepositoryTest(EntityManager entityManager, CityRepository cityRepository) {
        super(entityManager);
        this.cityRepository = cityRepository;
    }

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
        cityRepository.update(lion);

        var actualResult = cityRepository.findById(lion.getId());

        actualResult.ifPresent(
                actual -> assertThat(actual.getName()).isEqualTo(lion.getName())
        );
    }

    @Test
    void findById() {
        var actualResult = cityRepository.findById(lion.getId());

        assertThat(actualResult).isPresent();
        actualResult.ifPresent(actual -> assertThat(actual).isEqualTo(lion));
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
                                    .map(r -> Objects.requireNonNull(r.get(0, City.class)).getName())
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

    private City getLondon() {
        return City.builder()
                   .name("London")
                   .country(uk)
                   .build();
    }
}