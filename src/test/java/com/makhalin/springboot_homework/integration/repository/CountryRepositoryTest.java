package com.makhalin.springboot_homework.integration.repository;

import com.makhalin.springboot_homework.entity.Country;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CountryRepositoryTest extends IntegrationTestBase {

    @Autowired
    private CountryRepository countryRepository;

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
        countryRepository.saveAndFlush(uk);
        var actualResult = countryRepository.findById(uk.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()
                               .getName()).isEqualTo(uk.getName());
    }

    @Test
    void findById() {
        var actualResult = countryRepository.findById(uk.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(uk);
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