package com.makhalin.springboot_homework.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Value
@FieldNameConstants
public class FlightCreateEditDto {

    @NotBlank(message = "Flight number should not be empty")
    String flightNo;

    @NotNull
    Integer departureAirportId;

    @NotNull
    Integer arrivalAirportId;

    Integer transitAirportId;

    @PastOrPresent(message = "Flight should not be in future")
    LocalDate departureDate;

    @Pattern(regexp = "\\d+", message = "Only numbers")
    Integer hours;

    @Pattern(regexp = "\\d+", message = "Only numbers")
    Integer minutes;

    @NotNull
    Integer aircraftId;
}
