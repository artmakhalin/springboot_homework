package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CityCreateEditDto;
import com.makhalin.springboot_homework.dto.CityReadDto;
import com.makhalin.springboot_homework.dto.CountryReadDto;
import com.makhalin.springboot_homework.entity.City;
import com.makhalin.springboot_homework.entity.Country;
import com.makhalin.springboot_homework.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CityMapperTest {

    @InjectMocks
    private CityMapper cityMapper = new CityMapperImpl();

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CountryMapper countryMapper;

    private City seattle;
    private Country usa;
    private CityReadDto seattleDto;
    private CountryReadDto usaDto;

    @BeforeEach
    void prepareObjects() {
        usa = Country.builder()
                             .name("USA")
                             .id(1)
                             .build();
        seattle = City.builder()
                      .name("Seattle")
                      .id(5)
                      .country(usa)
                      .build();
        usaDto = new CountryReadDto(1, "USA");
        seattleDto = new CityReadDto(5, "Seattle", usaDto);
    }

    @Test
    void mapRead() {
        doReturn(usaDto).when(countryMapper).mapRead(usa);
        var actualResult = cityMapper.mapRead(seattle);

        assertThat(actualResult).isEqualTo(seattleDto);
        verify(countryMapper).mapRead(usa);
    }

    @Test
    void mapCreate() {
        doReturn(Optional.of(usa)).when(countryRepository).findById(usa.getId());
        var actualResult = cityMapper.mapCreate(new CityCreateEditDto("Seattle", 1));

        assertThat(actualResult).isEqualTo(seattle);
        verify(countryRepository).findById(usa.getId());
    }

    @Test
    void mapUpdate() {
        doReturn(Optional.of(usa)).when(countryRepository).findById(usa.getId());
        var actualResult = cityMapper.mapUpdate(new CityCreateEditDto("Miami", 1), seattle);

        assertThat(actualResult.getName()).isEqualTo("Miami");
        verify(countryRepository).findById(usa.getId());
    }
}