package com.makhalin.springboot_homework.integration;

import com.makhalin.springboot_homework.entity.ClassOfService;
import com.makhalin.springboot_homework.entity.Flight;
import com.makhalin.springboot_homework.entity.FlightCrew;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import static com.makhalin.springboot_homework.util.TestMocks.FlightMock.getJfkSea;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class FlightCrewIT extends IntegrationTestBase {

    private Flight flight;

    public FlightCrewIT(EntityManager entityManager) {
        super(entityManager);
    }

    @BeforeEach
    void saveFlight() {
        flight = getJfkSea();
        flight.setDepartureAirport(jfk);
        flight.setArrivalAirport(sea);
        flight.setAircraft(boeing737);
        entityManager.persist(flight);
    }

    @Test
    void saveSuccess() {
        var flightCrew = getJfkSeaAlex();
        flightCrew.setFlight(flight);
        entityManager.persist(flightCrew);

        assertThat(flightCrew.getId()).isNotNull();
    }

    @Test
    void saveShouldThrowExceptionClassOfServiceIsNull() {
        var flightCrew = getJfkSeaAlex();
        flightCrew.setFlight(flight);
        flightCrew.setClassOfService(null);

        assertThatExceptionOfType(PersistenceException.class).isThrownBy(() -> entityManager.persist(flightCrew));
    }

    @Test
    void getSuccess() {
        var flightCrew = getJfkSeaAlex();
        flightCrew.setFlight(flight);
        entityManager.persist(flightCrew);

        var actualResult = entityManager.find(FlightCrew.class, flightCrew.getId());

        assertThat(actualResult).isEqualTo(flightCrew);
    }

    @Test
    void updateSuccess() {
        var flightCrew = getJfkSeaAlex();
        flightCrew.setFlight(flight);
        entityManager.persist(flightCrew);
        flightCrew.setClassOfService(ClassOfService.ECONOMY);
        flightCrew.setIsTurnaround(true);
        entityManager.persist(flightCrew);
        entityManager.flush();

        var actualResult = entityManager.find(FlightCrew.class, flightCrew.getId());

        assertAll(
                () -> assertThat(actualResult.getClassOfService()).isEqualTo(flightCrew.getClassOfService()),
                () -> assertThat(actualResult.getIsTurnaround()).isEqualTo(flightCrew.getIsTurnaround())
        );
    }

    @Test
    void deleteSuccess() {
        var flightCrew = getJfkSeaAlex();
        flightCrew.setFlight(flight);
        entityManager.persist(flightCrew);
        entityManager.remove(flightCrew);
        entityManager.flush();

        var actualResult = entityManager.find(FlightCrew.class, flightCrew.getId());

        assertThat(actualResult).isNull();
    }
}
