package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.Country;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CountryRepositoryTest extends IntegrationTestBase {

    private final CountryRepository countryRepository;

    public CountryRepositoryTest(EntityManager entityManager, CountryRepository countryRepository) {
        super(entityManager);
        this.countryRepository = countryRepository;
    }

    @Test
    void save() {
        var germany = getGermany();
        countryRepository.save(germany);

        assertThat(germany.getId()).isNotNull();
    }

    @Test
    void delete() {
        countryRepository.delete(uk);
        var actualResult = countryRepository.findById(uk.getId());

        assertThat(actualResult).isEmpty();
    }

    @Test
    void update() {
        uk.setName("Spain");
        countryRepository.update(uk);
        var actualResult = countryRepository.findById(uk.getId());

        actualResult.ifPresent(
                actual -> assertThat(actual.getName()).isEqualTo(uk.getName())
        );
    }

    @Test
    void findById() {
        var actualResult = countryRepository.findById(uk.getId());

        assertThat(actualResult).isPresent();
        actualResult.ifPresent(actual -> assertThat(actual).isEqualTo(uk));
    }

    @Test
    void findAll() {
        var actualResults = countryRepository.findAll();
        var countryNames = actualResults.stream()
                                        .map(Country::getName)
                                        .toList();

        assertAll(
                () -> assertThat(actualResults).hasSize(4),
                () -> assertThat(countryNames).containsExactlyInAnyOrder(
                        usa.getName(),
                        russia.getName(),
                        france.getName(),
                        uk.getName()
                )
        );
    }

    private Country getGermany() {
        return Country.builder()
                      .name("Germany")
                      .build();
    }
}