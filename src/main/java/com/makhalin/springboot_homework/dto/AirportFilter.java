package com.makhalin.springboot_homework.dto;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@Builder
@FieldNameConstants
public class AirportFilter {

    String city;
    String country;
    String airport;
}
