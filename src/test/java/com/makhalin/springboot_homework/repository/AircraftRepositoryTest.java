package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.Aircraft;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AircraftRepositoryTest extends IntegrationTestBase {

    private final AircraftRepository aircraftRepository;

    public AircraftRepositoryTest(EntityManager entityManager, AircraftRepository aircraftRepository) {
        super(entityManager);
        this.aircraftRepository = aircraftRepository;
    }

    @Test
    void save() {
        var airbus350 = getAirbus350();
        aircraftRepository.save(airbus350);

        assertThat(airbus350.getId()).isNotNull();
    }

    @Test
    void delete() {
        aircraftRepository.delete(airbus330);
        var actualResult = aircraftRepository.findById(airbus330.getId());

        assertThat(actualResult).isEmpty();
    }

    @Test
    void update() {
        airbus330.setModel("TU-204");
        aircraftRepository.update(airbus330);

        var actualResult = aircraftRepository.findById(airbus330.getId());

        actualResult.ifPresent(
                actual -> assertThat(actual.getModel()).isEqualTo(airbus330.getModel())
        );
    }

    @Test
    void findById() {
        var actualResult = aircraftRepository.findById(airbus330.getId());

        assertThat(actualResult).isPresent();
        actualResult.ifPresent(actual -> assertThat(actual).isEqualTo(airbus330));
    }

    @Test
    void findAll() {
        var actualResults = aircraftRepository.findAll();
        var models = actualResults.stream()
                                  .map(Aircraft::getModel)
                                  .toList();

        assertAll(
                () -> assertThat(actualResults).hasSize(4),
                () -> assertThat(models).containsExactlyInAnyOrder(
                        boeing737.getModel(),
                        boeing777.getModel(),
                        airbus320.getModel(),
                        airbus330.getModel()
                )
        );
    }

    private Aircraft getAirbus350() {
        return Aircraft.builder()
                       .model("Airbus-350")
                       .build();
    }
}