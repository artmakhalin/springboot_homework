package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CityCreateEditDto;
import com.makhalin.springboot_homework.entity.City;
import com.makhalin.springboot_homework.entity.Country;
import com.makhalin.springboot_homework.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CityCreateEditMapperTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CityCreateEditMapper cityCreateEditMapper;

    @Test
    void map() {
        var usa = Country.builder()
                         .name("USA")
                         .id(1)
                         .build();
        doReturn(Optional.of(usa))
                .when(countryRepository)
                .findById(1);
        var dto = new CityCreateEditDto("Seattle", 1);

        var actualResult = cityCreateEditMapper.map(dto);
        var expectedResult = City.builder()
                                 .name("Seattle")
                                 .country(usa)
                                 .build();

        assertThat(actualResult).isEqualTo(expectedResult);
        verify(countryRepository).findById(1);
    }
}