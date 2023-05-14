package com.makhalin.springboot_homework.integration.repository;

import com.makhalin.springboot_homework.entity.ClassOfService;
import com.makhalin.springboot_homework.entity.FlightCrew;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.repository.FlightCrewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class FlightCrewRepositoryTest extends IntegrationTestBase {

    @Autowired
    private FlightCrewRepository flightCrewRepository;

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
        flightCrewRepository.saveAndFlush(ledVogAlex);

        var actualResult = flightCrewRepository.findById(ledVogAlex.getId());

        assertThat(actualResult).isPresent();
        assertAll(
                () -> assertThat(actualResult.get()
                                             .getClassOfService()).isEqualTo(ledVogAlex.getClassOfService()),
                () -> assertThat(actualResult.get()
                                             .getIsPassenger()).isEqualTo(ledVogAlex.getIsPassenger())
        );
    }

    @Test
    void findById() {
        var actualResult = flightCrewRepository.findById(ledVogAlex.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(ledVogAlex);
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