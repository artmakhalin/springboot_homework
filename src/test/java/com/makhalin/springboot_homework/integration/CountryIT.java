package com.makhalin.springboot_homework.integration;

import com.makhalin.springboot_homework.entity.Country;
import com.makhalin.springboot_homework.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
class CountryIT {

    private final EntityManager entityManager;

    @Test
    void saveSuccess() {
        var country = getUsa();
        entityManager.persist(country);

        assertThat(country.getId()).isNotNull();
    }

    @Test
    void saveShouldThrowExceptionIfUniqueConstraintViolated() {
        var country = getUsa();
        entityManager.persist(country);

        var country2 = getUsa();

        assertThatExceptionOfType(PersistenceException.class)
                .isThrownBy(() -> entityManager.persist(country2));
    }

    @Test
    void getSuccess() {
        var country = getUsa();
        entityManager.persist(country);

        var actualResult = entityManager.find(Country.class, country.getId());

        assertThat(actualResult).isEqualTo(country);
    }

    @Test
    void updateSuccess() {
        var country = getUsa();
        entityManager.persist(country);
        country.setName("France");
        entityManager.merge(country);

        var actualResult = entityManager.find(Country.class, country.getId());

        assertThat(actualResult.getName()).isEqualTo(country.getName());
    }

    @Test
    void deleteSuccess() {
        var country = getUsa();
        entityManager.persist(country);
        entityManager.remove(country);

        var actualResult = entityManager.find(Country.class, country.getId());

        assertThat(actualResult).isNull();
    }

    private Country getUsa() {
        return Country.builder()
                      .name("USA")
                      .build();
    }
}
