package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.FlightCrewCreateEditDto;
import com.makhalin.springboot_homework.entity.ClassOfService;
import com.makhalin.springboot_homework.repository.CrewRepository;
import com.makhalin.springboot_homework.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

class FlightCrewMapperTest extends MapperTestBase {

    @InjectMocks
    private FlightCrewMapper flightCrewMapper = new FlightCrewMapperImpl();

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private CrewRepository crewRepository;

    @Mock
    private FlightMapper flightMapper;

    @Mock
    private CrewMapper crewMapper;

    @Test
    void mapRead() {
        doReturn(jfkSeaDto).when(flightMapper)
                           .mapRead(jfkSea);
        doReturn(alexDto).when(crewMapper)
                         .mapRead(alex);

        var actualResult = flightCrewMapper.mapRead(alexJfkSea);

        assertThat(actualResult).isEqualTo(alexJfkSeaDto);
        verify(flightMapper).mapRead(jfkSea);
        verify(crewMapper).mapRead(alex);
    }

    @Test
    void mapCreate() {
        doReturn(Optional.of(jfkSea)).when(flightRepository)
                                     .findById(jfkSea.getId());
        doReturn(Optional.of(alex)).when(crewRepository)
                                   .findById(alex.getId());

        var actualResult = flightCrewMapper.mapCreate(new FlightCrewCreateEditDto(
                jfkSea.getId(),
                alex.getId(),
                ClassOfService.BUSINESS,
                true,
                true
        ));

        assertAll(
                () -> assertThat(actualResult.getCrew()
                                             .getEmail()).isEqualTo(alex.getEmail()),
                () -> assertThat(actualResult.getFlight()
                                             .getFlightNo()).isEqualTo(jfkSea.getFlightNo()),
                () -> assertThat(actualResult.getIsPassenger()).isTrue(),
                () -> assertThat(actualResult.getIsTurnaround()).isTrue(),
                () -> assertThat(actualResult.getClassOfService()).isSameAs(ClassOfService.BUSINESS)
        );
        verify(flightRepository).findById(jfkSea.getId());
        verify(crewRepository).findById(alex.getId());
    }

    @Test
    void mapUpdate() {
        doReturn(Optional.of(jfkSea)).when(flightRepository)
                                     .findById(jfkSea.getId());
        doReturn(Optional.of(alex)).when(crewRepository)
                                   .findById(alex.getId());

        var actualResult = flightCrewMapper.mapUpdate(new FlightCrewCreateEditDto(
                        jfkSea.getId(),
                        alex.getId(),
                        ClassOfService.ECONOMY,
                        false,
                        false
                ),
                alexJfkSea);

        assertAll(
                () -> assertThat(actualResult.getIsPassenger()).isFalse(),
                () -> assertThat(actualResult.getIsTurnaround()).isFalse(),
                () -> assertThat(actualResult.getClassOfService()).isSameAs(ClassOfService.ECONOMY)
        );
        verify(flightRepository).findById(jfkSea.getId());
        verify(crewRepository).findById(alex.getId());
    }
}