package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.ClassOfService;
import com.makhalin.springboot_homework.entity.FlightCrew;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class FlightCrewRepositoryTest extends IntegrationTestBase {

    private final FlightCrewRepository flightCrewRepository;

    FlightCrewRepositoryTest(EntityManager entityManager, FlightCrewRepository flightCrewRepository) {
        super(entityManager);
        this.flightCrewRepository = flightCrewRepository;
    }

    @Test
    void save() {
        var jfkCdgAlex = getJfkCdgAlex();
        flightCrewRepository.save(jfkCdgAlex);

        assertThat(jfkCdgAlex.getId()).isNotNull();
    }

    @Test
    void delete() {
        flightCrewRepository.delete(ledVogAlex);
        var actualResult = flightCrewRepository.findById(ledVogAlex.getId());

        assertThat(actualResult).isEmpty();
    }

    @Test
    void update() {
        ledVogAlex.setClassOfService(ClassOfService.ECONOMY);
        ledVogAlex.setIsPassenger(true);
        flightCrewRepository.update(ledVogAlex);

        var actualResult = flightCrewRepository.findById(ledVogAlex.getId());

        actualResult.ifPresent(
                actual -> assertAll(
                        () -> assertThat(actual.getClassOfService()).isEqualTo(ledVogAlex.getClassOfService()),
                        () -> assertThat(actual.getIsPassenger()).isEqualTo(ledVogAlex.getIsPassenger())
                )
        );
    }

    @Test
    void findById() {
        var actualResult = flightCrewRepository.findById(ledVogAlex.getId());

        assertThat(actualResult).isPresent();
        actualResult.ifPresent(actual -> assertThat(actual).isEqualTo(ledVogAlex));
    }

    @Test
    void findAll() {
        var actualResults = flightCrewRepository.findAll();

        assertThat(actualResults).hasSize(6);
    }

    private FlightCrew getJfkCdgAlex() {
        return FlightCrew.builder()
                         .classOfService(ClassOfService.BUSINESS)
                         .flight(jfkCdg)
                         .crew(alex)
                         .isTurnaround(false)
                         .isPassenger(false)
                         .build();
    }
}