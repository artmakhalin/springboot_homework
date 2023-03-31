package com.makhalin.springboot_homework.integration;

import com.makhalin.springboot_homework.entity.Aircraft;
import com.makhalin.springboot_homework.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@IT
@RequiredArgsConstructor
class AircraftIT {

    private final EntityManager entityManager;

    @Test
    void saveSuccess() {
        var aircraft = getBoeing();
        entityManager.persist(aircraft);

        assertThat(aircraft.getId()).isNotNull();
    }

    @Test
    void saveShouldThrowExceptionIfUniqueConstraintViolated() {
        var aircraft = getBoeing();
        entityManager.persist(aircraft);

        var aircraft2 = getBoeing();

        assertThatExceptionOfType(PersistenceException.class)
                .isThrownBy(() -> entityManager.persist(aircraft2));
    }

    @Test
    void getSuccess() {
        var aircraft = getBoeing();
        entityManager.persist(aircraft);

        var actualResult = entityManager.find(Aircraft.class, aircraft.getId());

        assertThat(actualResult).isEqualTo(aircraft);
    }

    @Test
    void updateSuccess() {
        var aircraft = getBoeing();
        entityManager.persist(aircraft);
        aircraft.setModel("Airbus-350");
        entityManager.merge(aircraft);
        entityManager.flush();

        var actualResult = entityManager.find(Aircraft.class, aircraft.getId());

        assertThat(actualResult.getModel()).isEqualTo(aircraft.getModel());
    }

    @Test
    void deleteSuccess() {
        var aircraft = getBoeing();
        entityManager.persist(aircraft);
        entityManager.remove(aircraft);
        entityManager.flush();

        var actualResult = entityManager.find(Aircraft.class, aircraft.getId());

        assertThat(actualResult).isNull();
    }

    private Aircraft getBoeing() {
        return Aircraft.builder()
                       .model("Boeing-737")
                       .build();
    }
}
