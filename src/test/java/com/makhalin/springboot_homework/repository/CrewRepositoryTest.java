package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.dto.CrewFilter;
import com.makhalin.springboot_homework.entity.Crew;
import com.makhalin.springboot_homework.entity.Role;
import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static com.makhalin.springboot_homework.util.TestMocks.CrewAircraftMock.getAlexBoeing737;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CrewRepositoryTest extends IntegrationTestBase {

    private final CrewRepository crewRepository;

    public CrewRepositoryTest(EntityManager entityManager, CrewRepository crewRepository) {
        super(entityManager);
        this.crewRepository = crewRepository;
    }

    @Test
    void save() {
        var liza = getLiza();
        crewRepository.save(liza);

        assertThat(liza.getId()).isNotNull();
    }

    @Test
    void delete() {
        crewRepository.delete(marta);
        var actualResult = crewRepository.findById(marta.getId());

        assertThat(actualResult).isEmpty();
    }

    @Test
    void update() {
        marta.setFirstname("Diana");
        marta.setEmail("diana@mail.ru");
        crewRepository.update(marta);

        var actualResult = crewRepository.findById(marta.getId());

        actualResult.ifPresent(
                actual -> assertAll(
                        () -> assertThat(actual.getFirstname()).isEqualTo(marta.getFirstname()),
                        () -> assertThat(actual.getEmail()).isEqualTo(marta.getEmail())
                )
        );
    }

    @Test
    void findById() {
        var actualResult = crewRepository.findById(marta.getId());

        assertThat(actualResult).isPresent();
        actualResult.ifPresent(actual -> assertThat(actual).isEqualTo(marta));
    }

    @Test
    void findAll() {
        var actualResults = crewRepository.findAll();
        var emails = actualResults.stream()
                                  .map(Crew::getEmail)
                                  .toList();

        assertAll(
                () -> assertThat(actualResults).hasSize(4),
                () -> assertThat(emails).containsExactlyInAnyOrder(
                        alex.getEmail(),
                        jake.getEmail(),
                        bob.getEmail(),
                        marta.getEmail()
                )
        );
    }

    @Test
    void findByAircraftAndEmploymentDate() {
        var alexBoeing737 = getAlexBoeing737();
        alexBoeing737.setCrew(alex);
        alexBoeing737.setAircraft(boeing737);
        entityManager.persist(alexBoeing737);
        var filter = CrewFilter.builder()
                               .startYear(2015)
                               .aircraftModel(boeing737.getModel())
                               .build();

        var actualResult = crewRepository.findByAircraftAndEmploymentDate(filter);
        var emails = actualResult.stream()
                                 .map(Crew::getEmail)
                                 .toList();

        assertThat(actualResult).hasSize(2);
        assertThat(emails).containsExactlyInAnyOrder(
                jake.getEmail(),
                alex.getEmail()
        );
    }

    private Crew getLiza() {
        return Crew.builder()
                   .firstname("Liza")
                   .lastname("Test")
                   .email("liza@test.com")
                   .password("test")
                   .birthDate(LocalDate.of(1990, 1, 1))
                   .employmentDate(LocalDate.of(2015, 4, 5))
                   .role(Role.USER)
                   .build();
    }
}