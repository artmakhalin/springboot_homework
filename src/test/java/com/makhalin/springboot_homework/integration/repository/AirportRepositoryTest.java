package com.makhalin.springboot_homework.integration.repository;

import com.makhalin.springboot_homework.entity.Airport;
import com.makhalin.springboot_homework.entity.City;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.repository.AirportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AirportRepositoryTest extends IntegrationTestBase {

    @Autowired
    private AirportRepository airportRepository;

    @Test
    void save() {
        var lga = getLga();
        airportRepository.save(lga);

        var actualResult = airportRepository.findById(lga.getCode());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(lga);
    }

    @Test
    void delete() {
        airportRepository.delete(ewr);
        var actualResult = airportRepository.findById(ewr.getCode());

        assertThat(actualResult).isEmpty();
    }

    @Test
    void update() {
        ewr.setCity(seattle);
        airportRepository.saveAndFlush(ewr);

        var actualResult = airportRepository.findById(ewr.getCode());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()
                               .getCity()).isEqualTo(ewr.getCity());

    }

    @Test
    void findById() {
        var actualResult = airportRepository.findById(jfk.getCode());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(jfk);
    }

    @Test
    void findAll() {
        var actualResults = airportRepository.findAll();
        var codes = actualResults.stream()
                                 .map(Airport::getCode)
                                 .toList();

        assertAll(
                () -> assertThat(actualResults).hasSize(7),
                () -> assertThat(codes).containsExactlyInAnyOrder(
                        jfk.getCode(),
                        sea.getCode(),
                        svo.getCode(),
                        led.getCode(),
                        vog.getCode(),
                        cdg.getCode(),
                        ewr.getCode()
                )
        );

    }

    @Test
    void findByCountryName() {
        var actualResult = airportRepository.findByCountryName(usa.getName());
        var cities = actualResult.stream()
                                 .map(Airport::getCity)
                                 .map(City::getName)
                                 .collect(toSet());

        assertThat(actualResult).hasSize(3);
        assertThat(cities).containsExactlyInAnyOrder(newYork.getName(), seattle.getName());
    }

    @Test
    void findAllByCityId() {
        var actualResult = airportRepository.findAllByCityId(newYork.getId());
        var airports = actualResult.stream()
                                   .map(Airport::getCode)
                                   .toList();

        assertThat(actualResult).hasSize(2);
        assertThat(airports).containsExactlyInAnyOrder(jfk.getCode(), ewr.getCode());
    }

    private Airport getLga() {
        return Airport.builder()
                      .code("LGA")
                      .city(newYork)
                      .build();
    }
}