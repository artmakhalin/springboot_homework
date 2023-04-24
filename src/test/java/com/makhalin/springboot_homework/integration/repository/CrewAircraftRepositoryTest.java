package com.makhalin.springboot_homework.integration.repository;

import com.makhalin.springboot_homework.entity.CrewAircraft;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.repository.CrewAircraftRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CrewAircraftRepositoryTest extends IntegrationTestBase {

    @Autowired
    private CrewAircraftRepository crewAircraftRepository;

    @Test
    void save() {
        var martaAirbus330 = getMartaAirbus330();
        crewAircraftRepository.save(martaAirbus330);

        assertThat(martaAirbus330.getId()).isNotNull();
    }

    @Test
    void delete() {
        crewAircraftRepository.delete(bobBoeing777);
        var actualResult = crewAircraftRepository.findById(bobBoeing777.getId());

        assertThat(actualResult).isEmpty();
    }

    @Test
    void update() {
        bobBoeing777.setPermitDate(LocalDate.of(2023, 1, 1));
        crewAircraftRepository.saveAndFlush(bobBoeing777);

        var actualResult = crewAircraftRepository.findById(bobBoeing777.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getPermitDate()).isEqualTo(bobBoeing777.getPermitDate());
    }

    @Test
    void findById() {
        var actualResult = crewAircraftRepository.findById(bobBoeing777.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(bobBoeing777);
    }

    @Test
    void findAll() {
        var actualResults = crewAircraftRepository.findAll();

        assertThat(actualResults).hasSize(5);
    }

    private CrewAircraft getMartaAirbus330() {
        return CrewAircraft.builder()
                           .permitDate(LocalDate.of(2021, 1, 1))
                           .crew(marta)
                           .aircraft(airbus330)
                           .build();
    }
}