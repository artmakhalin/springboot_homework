package com.makhalin.springboot_homework.integration.service;

import com.makhalin.springboot_homework.dto.CrewCreateEditDto;
import com.makhalin.springboot_homework.dto.CrewFilter;
import com.makhalin.springboot_homework.dto.CrewReadDto;
import com.makhalin.springboot_homework.exception.BadRequestException;
import com.makhalin.springboot_homework.exception.NotFoundException;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.mapper.CrewMapper;
import com.makhalin.springboot_homework.service.CrewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CrewServiceIT extends IntegrationTestBase {

    @Autowired
    private CrewService crewService;

    @Autowired
    private CrewMapper crewMapper;

    @Test
    void loadUserByUsername() {
        var expectedUser = new User(
                alex.getEmail(),
                alex.getPassword(),
                Collections.singleton(alex.getRole())
        );

        var actualResult = crewService.loadUserByUsername(alex.getEmail());

        assertThat(actualResult).isEqualTo(expectedUser);
    }

    @Test
    void shouldThrowUsernameNotFoundExceptionIfNoUserByUsername() {
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> crewService.loadUserByUsername("dummy@test.com"));
    }

    @Test
    void findByEmail() {
        var expectedResult = crewMapper.mapRead(alex);

        var actualResult = crewService.findByEmail(alex.getEmail());

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowNotFoundExceptionIfNoCrewByEmail() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> crewService.findByEmail("dummy@test.com"));
    }

    @Test
    void create() {
        var crewDto = new CrewCreateEditDto(
                "vlad@test.com",
                "test",
                "Vlad",
                "Test",
                LocalDate.of(1990, 1, 1),
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2023, 12, 4)
        );

        var actualResult = crewService.create(crewDto);

        assertThat(actualResult.getId()).isNotNull();
    }

    @Test
    void shouldThrowBadRequestExceptionIfCrewYoungerThanEighteen() {
        var crewDto = new CrewCreateEditDto(
                "vlad@test.com",
                "test",
                "Vlad",
                "Test",
                LocalDate.of(2010, 1, 1),
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2023, 12, 4)
        );

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> crewService.create(crewDto));
    }

    @Test
    void findAllByFilter() {
        var filter = CrewFilter.builder()
                               .email("@test")
                               .build();

        var actualResult = crewService.findAllByFilter(filter, PageRequest.of(0, 20));
        var crewEmails = actualResult.get()
                                     .map(CrewReadDto::getEmail)
                                     .toList();

        assertAll(
                () -> assertThat(actualResult.getTotalElements()).isEqualTo(4L),
                () -> assertThat(crewEmails).containsExactlyInAnyOrder(
                        alex.getEmail(),
                        bob.getEmail(),
                        jake.getEmail(),
                        marta.getEmail()
                )
        );
    }

    @Test
    void findById() {
        var actualResult = crewService.findById(alex.getId());

        assertThat(actualResult.getEmail()).isEqualTo(alex.getEmail());
    }

    @Test
    void shouldThrowNotFoundExceptionIfNoUserById() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> crewService.findById(555));
    }

    @Test
    void update() {
        var crewDto = new CrewCreateEditDto(
                "vlad@test.com",
                "test",
                "Alex",
                "Test",
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2020, 1, 1),
                null
        );

        var actualResult = crewService.update(alex.getId(), crewDto);

        assertAll(
                () -> assertThat(actualResult.getEmail()).isEqualTo(crewDto.getEmail()),
                () -> assertThat(actualResult.getFirstname()).isEqualTo(crewDto.getFirstname()),
                () -> assertThat(actualResult.getBirthDate()).isEqualTo(crewDto.getBirthDate()),
                () -> assertThat(actualResult.getEmploymentDate()).isEqualTo(crewDto.getEmploymentDate())
        );
    }

    @Test
    void delete() {
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> crewService.delete(marta.getId())),
                () -> assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> crewService.delete(555))
        );
    }
}
