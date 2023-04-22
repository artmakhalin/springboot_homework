package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.AircraftCreateEditDto;
import com.makhalin.springboot_homework.dto.AircraftReadDto;
import com.makhalin.springboot_homework.entity.Aircraft;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AircraftMapperTest {

    @Autowired
    private AircraftMapper aircraftMapper;

    @Test
    void mapRead() {
        var aircraft = getAircraft();

        var actualResult = aircraftMapper.mapRead(aircraft);
        var expectedResult = new AircraftReadDto(1, "Airbus-320");

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void mapCreate() {
        var dto = new AircraftCreateEditDto("Airbus-320");

        var actualResult = aircraftMapper.mapCreate(dto);

        assertThat(actualResult.getModel()).isEqualTo(dto.getModel());
    }

    @Test
    void mapUpdate() {
        var aircraft = getAircraft();
        var dto = new AircraftCreateEditDto("Boeing-777");

        var actualResult = aircraftMapper.mapUpdate(dto, aircraft);

        assertThat(actualResult.getModel()).isEqualTo(dto.getModel());
    }

    private Aircraft getAircraft() {
        return Aircraft.builder()
                       .id(1)
                       .model("Airbus-320")
                       .build();
    }
}