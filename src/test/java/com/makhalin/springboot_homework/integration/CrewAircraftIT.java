package com.makhalin.springboot_homework.integration;

import com.makhalin.springboot_homework.entity.CrewAircraft;
import com.makhalin.springboot_homework.util.TestMocks.CrewAircraftMock;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CrewAircraftIT extends IntegrationTestBase {

    public CrewAircraftIT(EntityManager entityManager) {
        super(entityManager);
    }

    @Test
    void saveSuccess() {
        var crewAircraft = getAlexBoeing737();
        entityManager.persist(crewAircraft);

        assertThat(crewAircraft.getId()).isNotNull();
    }

    @Test
    void saveShouldThrowExceptionIfPermitDateIsNull() {
        var crewAircraft = getAlexBoeing737();
        crewAircraft.setPermitDate(null);

        assertThatExceptionOfType(PersistenceException.class).isThrownBy(() -> entityManager.persist(crewAircraft));
    }

    @Test
    void getSuccess() {
        var crewAircraft = getAlexBoeing737();
        entityManager.persist(crewAircraft);

        var actualResult = entityManager.find(CrewAircraft.class, crewAircraft.getId());

        assertThat(actualResult).isEqualTo(crewAircraft);
    }

    @Test
    void updateSuccess() {
        var crewAircraft = getAlexBoeing737();
        entityManager.persist(crewAircraft);
        crewAircraft.setPermitDate(LocalDate.of(2018, 5, 5));
        entityManager.merge(crewAircraft);

        var actualResult = entityManager.find(CrewAircraft.class, crewAircraft.getId());

        assertThat(actualResult.getPermitDate()).isEqualTo(crewAircraft.getPermitDate());
    }

    @Test
    void deleteSuccess() {
        var crewAircraft = getAlexBoeing737();
        entityManager.persist(crewAircraft);
        entityManager.remove(crewAircraft);

        var actualResult = entityManager.find(CrewAircraft.class, crewAircraft.getId());

        assertThat(actualResult).isNull();
    }

    private CrewAircraft getAlexBoeing737() {
        var crewAircraft = CrewAircraftMock.getAlexBoeing737();
        crewAircraft.setCrew(alex);
        crewAircraft.setAircraft(boeing737);

        return crewAircraft;
    }
}
