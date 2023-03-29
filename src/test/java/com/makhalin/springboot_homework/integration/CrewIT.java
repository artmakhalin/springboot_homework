package com.makhalin.springboot_homework.integration;

import com.makhalin.springboot_homework.entity.Crew;
import com.makhalin.springboot_homework.entity.Role;
import com.makhalin.springboot_homework.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@IT
@RequiredArgsConstructor
class CrewIT {

    private final EntityManager entityManager;

    @Test
    void saveSuccess() {
        var crew = getAlex();
        entityManager.persist(crew);

        assertThat(crew.getId()).isNotNull();
    }

    @Test
    void saveShouldThrowExceptionIfUniqueConstraintViolated() {
        var crew = getAlex();
        entityManager.persist(crew);

        var crew2 = getAlex();

        assertThatExceptionOfType(PersistenceException.class)
                .isThrownBy(() -> entityManager.persist(crew2));
    }

    @Test
    void getSuccess() {
        var crew = getAlex();
        entityManager.persist(crew);

        var actualResult = entityManager.find(Crew.class, crew.getId());

        assertThat(actualResult).isEqualTo(crew);
    }

    @Test
    void updateSuccess() {
        var crew = getAlex();
        entityManager.persist(crew);
        crew.setFirstname("Peter");
        crew.setEmail("peter@test.com");
        crew.setMkkDate(LocalDate.of(2023, 10, 10));
        crew.setRole(Role.ADMIN);
        entityManager.merge(crew);

        var actualResult = entityManager.find(Crew.class, crew.getId());

        assertAll(
                () -> assertThat(actualResult.getFirstname()).isEqualTo(crew.getFirstname()),
                () -> assertThat(actualResult.getEmail()).isEqualTo(crew.getEmail()),
                () -> assertThat(actualResult.getMkkDate()).isEqualTo(crew.getMkkDate()),
                () -> assertThat(actualResult.getRole()).isEqualTo(crew.getRole())
        );
    }

    @Test
    void deleteSuccess() {
        var crew = getAlex();
        entityManager.persist(crew);
        entityManager.remove(crew);

        var actualResult = entityManager.find(Crew.class, crew.getId());

        assertThat(actualResult).isNull();
    }

    private Crew getAlex() {
        return Crew.builder()
                   .firstname("Alex")
                   .lastname("Test")
                   .email("alex@test.com")
                   .password("test")
                   .birthDate(LocalDate.of(1995, 1, 1))
                   .employmentDate(LocalDate.of(2015, 12, 5))
                   .role(Role.USER)
                   .build();
    }
}
