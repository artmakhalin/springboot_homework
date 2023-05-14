package com.makhalin.springboot_homework.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Value
@FieldNameConstants
public class CrewAircraftCreateEditDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Permit date should not be in future")
    LocalDate permitDate;

    @NotNull
    Integer crewId;

    @NotNull
    Integer aircraftId;
}
