package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.AircraftReadDto;
import com.makhalin.springboot_homework.dto.CrewAircraftCreateEditDto;
import com.makhalin.springboot_homework.dto.CrewAircraftReadDto;
import com.makhalin.springboot_homework.dto.CrewReadDto;
import com.makhalin.springboot_homework.entity.*;
import com.makhalin.springboot_homework.repository.AircraftRepository;
import com.makhalin.springboot_homework.repository.CrewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CrewAircraftMapperTest {

    @InjectMocks
    private CrewAircraftMapper crewAircraftMapper = new CrewAircraftMapperImpl();

    @Mock
    private CrewRepository crewRepository;

    @Mock
    private AircraftRepository aircraftRepository;

    @Mock
    private CrewMapper crewMapper;

    @Mock
    private AircraftMapper aircraftMapper;

    private Crew alex;
    private Aircraft boeing;
    private CrewAircraft alexBoeing;
    private CrewAircraftReadDto alexBoeingDto;
    private CrewReadDto alexDto;
    private AircraftReadDto boeingDto;

    @BeforeEach
    void prepareObjects() {
        alex = Crew.builder()
                   .id(1)
                   .personalInfo(PersonalInfo.builder()
                                             .firstname("Alex")
                                             .lastname("Test")
                                             .birthDate(LocalDate.of(1995, 1, 1))
                                             .build())
                   .email("alex@test.com")
                   .password("{noop}test")
                   .employmentDate(LocalDate.of(2015, 12, 5))
                   .role(Role.USER)
                   .build();
        boeing = Aircraft.builder()
                         .id(5)
                         .model("Boeing-777")
                         .build();
        alexBoeing = CrewAircraft.builder()
                                 .id(1L)
                                 .permitDate(LocalDate.of(2018, 1, 1))
                                 .crew(alex)
                                 .aircraft(boeing)
                                 .build();
        alexDto = new CrewReadDto(
                1,
                "alex@test.com",
                "Alex",
                "Test",
                LocalDate.of(1995, 1, 1),
                LocalDate.of(2015, 12, 5),
                null,
                Role.USER
        );
        boeingDto = new AircraftReadDto(5, "Boeing-777");
        alexBoeingDto = new CrewAircraftReadDto(
                1L,
                LocalDate.of(2018, 1, 1),
                alexDto,
                boeingDto
        );
    }

    @Test
    void mapRead() {
        doReturn(alexDto).when(crewMapper).mapRead(alex);
        doReturn(boeingDto).when(aircraftMapper).mapRead(boeing);
        var actualResult = crewAircraftMapper.mapRead(alexBoeing);

        assertThat(actualResult).isEqualTo(alexBoeingDto);
        verify(crewMapper).mapRead(alex);
        verify(aircraftMapper).mapRead(boeing);
    }

    @Test
    void mapCreate() {
        doReturn(Optional.of(alex)).when(crewRepository).findById(alex.getId());
        doReturn(Optional.of(boeing)).when(aircraftRepository).findById(boeing.getId());
        var actualResult = crewAircraftMapper.mapCreate(new CrewAircraftCreateEditDto(
                LocalDate.of(2018, 1, 1),
                alex.getId(),
                boeing.getId())
        );

        assertAll(
                () -> assertThat(actualResult.getPermitDate()).isEqualTo(alexBoeing.getPermitDate()),
                () -> assertThat(actualResult.getCrew()).isEqualTo(alexBoeing.getCrew()),
                () -> assertThat(actualResult.getPermitDate()).isEqualTo(alexBoeing.getPermitDate())
        );
        verify(crewRepository).findById(alex.getId());
        verify(aircraftRepository).findById(boeing.getId());
    }

    @Test
    void mapUpdate() {
        doReturn(Optional.of(alex)).when(crewRepository).findById(alex.getId());
        doReturn(Optional.of(boeing)).when(aircraftRepository).findById(boeing.getId());
        var actualResult = crewAircraftMapper.mapUpdate(new CrewAircraftCreateEditDto(
                LocalDate.of(2020, 5, 5),
                alex.getId(),
                boeing.getId()),
                alexBoeing
        );

        assertThat(actualResult.getPermitDate()).isEqualTo(LocalDate.of(2020, 5, 5));
        verify(crewRepository).findById(alex.getId());
        verify(aircraftRepository).findById(boeing.getId());
    }
}