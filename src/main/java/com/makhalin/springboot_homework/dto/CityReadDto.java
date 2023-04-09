package com.makhalin.springboot_homework.dto;

import lombok.Value;

@Value
public class CityReadDto {

    Integer id;
    String name;
    CountryReadDto country;
}
