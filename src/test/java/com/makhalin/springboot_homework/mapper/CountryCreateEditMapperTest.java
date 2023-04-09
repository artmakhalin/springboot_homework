package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CountryCreateEditDto;
import com.makhalin.springboot_homework.entity.Country;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CountryCreateEditMapperTest {

    @InjectMocks
    private CountryCreateEditMapper countryCreateEditMapper;

    @Test
    void map() {
        var dto = new CountryCreateEditDto("Spain");

        var actualResult = countryCreateEditMapper.map(dto);

        assertThat(actualResult.getName()).isEqualTo(dto.getName());
    }
}