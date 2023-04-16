package com.makhalin.springboot_homework.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@FieldNameConstants
public class CityCreateEditDto {

    @Size(min = 3, max = 32)
    String name;

    @NotNull
    Integer countryId;
}
