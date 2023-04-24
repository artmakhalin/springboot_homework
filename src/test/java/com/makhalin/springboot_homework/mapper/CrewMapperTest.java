package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CrewCreateEditDto;
import com.makhalin.springboot_homework.dto.CrewReadDto;
import com.makhalin.springboot_homework.entity.Crew;
import com.makhalin.springboot_homework.entity.PersonalInfo;
import com.makhalin.springboot_homework.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class CrewMapperTest {

    @Autowired
    private CrewMapper crewMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void mapRead() {
        var crew = getAlex();
        crew.setId(1);
        var dto = new CrewReadDto(
                1,
                "alex@test.com",
                "Alex",
                "Test",
                LocalDate.of(1990, 1, 1),
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2023, 12, 4),
                Role.USER
        );
        var actualResult = crewMapper.mapRead(crew);

        assertThat(actualResult).isEqualTo(dto);
    }

    @Test
    void mapCreate() {
        var crew = getAlex();
        crew.setPassword(passwordEncoder.encode("test"));
        var dto = new CrewCreateEditDto(
                "alex@test.com",
                "test",
                "Alex",
                "Test",
                LocalDate.of(1990, 1, 1),
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2023, 12, 4)
        );

        var actualResult = crewMapper.mapCreate(dto);

        assertThat(actualResult).isEqualTo(crew);
    }

    @Test
    void mapUpdate() {
        var crew = getAlex();
        var dto = new CrewCreateEditDto(
                "john@test.com",
                "test",
                "John",
                "Test",
                LocalDate.of(1980, 1, 1),
                LocalDate.of(2010, 1, 1),
                LocalDate.of(2024, 12, 4)
        );

        var actualResult = crewMapper.mapUpdate(dto, crew);

        assertAll(
                () -> assertThat(actualResult.getEmail()).isEqualTo(dto.getEmail()),
                () -> assertThat(actualResult.getPersonalInfo()
                                             .getFirstname()).isEqualTo(dto.getFirstname()),
                () -> assertThat(actualResult.getPersonalInfo()
                                             .getBirthDate()).isEqualTo(dto.getBirthDate()),
                () -> assertThat(actualResult.getEmploymentDate()).isEqualTo(dto.getEmploymentDate()),
                () -> assertThat(actualResult.getMkkDate()).isEqualTo(dto.getMkkDate())
        );
    }

    private Crew getAlex() {
        return Crew.builder()
                   .email("alex@test.com")
                   .personalInfo(
                           PersonalInfo.builder()
                                       .firstname("Alex")
                                       .lastname("Test")
                                       .birthDate(LocalDate.of(1990, 1, 1))
                                       .build()
                   )
                   .employmentDate(LocalDate.of(2020, 1, 1))
                   .mkkDate(LocalDate.of(2023, 12, 4))
                   .role(Role.USER)
                   .build();
    }
}