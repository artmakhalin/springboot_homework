package com.makhalin.springboot_homework.dto;

import lombok.Value;

@Value
public class AirportReadDto {

    Integer id;
    String code;
    CityReadDto city;
}
