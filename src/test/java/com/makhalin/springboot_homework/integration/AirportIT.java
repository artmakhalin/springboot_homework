package com.makhalin.springboot_homework.integration;

import com.makhalin.springboot_homework.entity.Airport;
import com.makhalin.springboot_homework.entity.City;
import com.makhalin.springboot_homework.entity.Country;
import com.makhalin.springboot_homework.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import static org.assertj.core.api.Assertions.*;

@IT
@RequiredArgsConstructor
class AirportIT {

    private final EntityManager entityManager;
    private Country usa;
    private City newYork;
    private City miami;

    @BeforeEach
    void prepareDatabase() {
        usa = getUsa();
        entityManager.persist(usa);
        newYork = getNewYork();
        entityManager.persist(newYork);
        miami = getMiami();
        entityManager.persist(miami);
    }

    @Test
    void saveSuccess() {
        var airport = getJfk();

        assertThatNoException().isThrownBy(() -> entityManager.persist(airport));
    }

    @Test
    void saveShouldThrowExceptionIfCityIsNull() {
        var airport = getJfk();
        airport.setCity(null);

        assertThatExceptionOfType(PersistenceException.class).isThrownBy(() -> entityManager.persist(airport));
    }

    @Test
    void getSuccess() {
        var airport = getJfk();
        entityManager.persist(airport);

        var actualResult = entityManager.find(Airport.class, airport.getCode());

        assertThat(actualResult).isEqualTo(airport);
    }

    @Test
    void updateSuccess() {
        var airport = getJfk();
        entityManager.persist(airport);
        airport.setCity(miami);
        entityManager.merge(airport);

        var actualResult = entityManager.find(Airport.class, airport.getCode());

        assertThat(actualResult.getCity()).isEqualTo(airport.getCity());

    }

    @Test
    void deleteSuccess() {
        var airport = getJfk();
        entityManager.persist(airport);
        entityManager.remove(airport);

        var actualResult = entityManager.find(Airport.class, airport.getCode());

        assertThat(actualResult).isNull();
    }

    private Airport getJfk() {
        return Airport.builder()
                      .code("JFK")
                      .city(newYork)
                      .build();
    }

    private City getNewYork() {
        return City.builder()
                   .name("New York")
                   .country(usa)
                   .build();
    }

    private City getMiami() {
        return City.builder()
                   .name("Miami")
                   .country(usa)
                   .build();
    }

    private Country getUsa() {
        return Country.builder()
                      .name("USA")
                      .build();
    }
}
