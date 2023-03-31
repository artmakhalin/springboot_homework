package com.makhalin.springboot_homework.integration;

import com.makhalin.springboot_homework.entity.Flight;
import com.makhalin.springboot_homework.util.TestMocks.FlightMock;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class FlightIT extends IntegrationTestBase {

    public FlightIT(EntityManager entityManager) {
        super(entityManager);
    }

    @Test
    void saveSuccess() {
        var flight = getJfkSea();
        entityManager.persist(flight);

        assertThat(flight.getId()).isNotNull();
    }

    @Test
    void saveShouldThrowExceptionIfAircraftIsNull() {
        var flight = getJfkSea();
        flight.setAircraft(null);

        assertThatExceptionOfType(PersistenceException.class).isThrownBy(() -> entityManager.persist(flight));
    }

    @Test
    void getSuccess() {
        var flight = getJfkSea();
        entityManager.persist(flight);

        var actualResult = entityManager.find(Flight.class, flight.getId());

        assertThat(actualResult).isEqualTo(flight);
    }

    @Test
    void updateSuccess() {
        var flight = getJfkSea();
        entityManager.persist(flight);
        flight.setFlightNo("X555");
        flight.setTime(3600L);
        flight.setDepartureDate(LocalDate.of(2020, 1, 1));
        entityManager.merge(flight);
        entityManager.flush();

        var actualResult = entityManager.find(Flight.class, flight.getId());

        assertAll(
                () -> assertThat(actualResult.getFlightNo()).isEqualTo(flight.getFlightNo()),
                () -> assertThat(actualResult.getTime()).isEqualTo(flight.getTime()),
                () -> assertThat(actualResult.getDepartureDate()).isEqualTo(flight.getDepartureDate())
        );
    }

    @Test
    void deleteSuccess() {
        var flight = getJfkSea();
        entityManager.persist(flight);
        entityManager.remove(flight);
        entityManager.flush();

        var actualResult = entityManager.find(Flight.class, flight.getId());

        assertThat(actualResult).isNull();
    }

    private Flight getJfkSea() {
        var flight = FlightMock.getJfkSea();
        flight.setDepartureAirport(jfk);
        flight.setArrivalAirport(sea);
        flight.setAircraft(boeing737);

        return flight;
    }
}
