package com.makhalin.springboot_homework.dto;

import lombok.Value;

@Value
public class AirportReadDto {

    String code;
    CityReadDto city;
}
