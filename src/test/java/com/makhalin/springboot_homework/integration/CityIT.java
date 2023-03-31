package com.makhalin.springboot_homework.integration;

import com.makhalin.springboot_homework.entity.City;
import com.makhalin.springboot_homework.entity.Country;
import com.makhalin.springboot_homework.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@IT
@RequiredArgsConstructor
class CityIT {

    private final EntityManager entityManager;
    private Country usa;
    private Country france;

    @BeforeEach
    void prepareDatabase() {
        usa = getUsa();
        entityManager.persist(usa);
        france = getFrance();
        entityManager.persist(france);
    }

    @Test
    void saveSuccess() {
        var city = getNewYork();
        entityManager.persist(city);

        assertThat(city.getId()).isNotNull();
    }

    @Test
    void saveShouldThrowExceptionIfCountryIsNull() {
        var city = getNewYork();
        city.setCountry(null);

        assertThatExceptionOfType(PersistenceException.class).isThrownBy(() -> entityManager.persist(city));
    }

    @Test
    void getSuccess() {
        var city = getNewYork();
        entityManager.persist(city);

        var actualResult = entityManager.find(City.class, city.getId());

        assertThat(actualResult).isEqualTo(city);
    }

    @Test
    void updateSuccess() {
        var city = getNewYork();
        entityManager.persist(city);
        city.setCountry(france);
        entityManager.merge(city);
        entityManager.flush();

        var actualResult = entityManager.find(City.class, city.getId());

        assertThat(actualResult.getCountry()).isEqualTo(city.getCountry());
    }

    @Test
    void deleteSuccess() {
        var city = getNewYork();
        entityManager.persist(city);
        entityManager.remove(city);
        entityManager.flush();

        var actualResult = entityManager.find(City.class, city.getId());

        assertThat(actualResult).isNull();
    }

    private Country getUsa() {
        return Country.builder()
                      .name("USA")
                      .build();
    }

    private Country getFrance() {
        return Country.builder()
                      .name("France")
                      .build();
    }

    private City getNewYork() {
        return City.builder()
                   .name("New York")
                   .country(usa)
                   .build();
    }
}
