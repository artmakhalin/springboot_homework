package com.makhalin.springboot_homework.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Flight could not be in future")
    LocalDate departureDate;

    @Min(value = 0, message = "Flight time could not be negative")
    @Max(value = 20, message = "Flight time could not greater than 20 h")
    Integer hours;

    @Min(value = 0, message = "Minutes could not negative")
    @Max(value = 59, message = "Minutes could not greater than 59")
    Integer minutes;

    @NotNull
    Integer aircraftId;
}
