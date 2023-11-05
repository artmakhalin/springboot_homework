package com.makhalin.springboot_homework.http.controller;

import com.makhalin.springboot_homework.dto.FlightFilter;
import com.makhalin.springboot_homework.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final FlightService flightService;

    @GetMapping("/monthly")
    public String monthlyFlights(@AuthenticationPrincipal UserDetails userDetails,
                                 Model model,
                                 @RequestParam(value = "month", required = false) Integer month,
                                 @RequestParam(value = "year", required = false) Integer year) {
        if (month == null || year == null) {
            month = LocalDate.now()
                             .getMonthValue();
            year = LocalDate.now()
                            .getYear();
        }
        var filter = FlightFilter.builder()
                                 .crewEmail(userDetails.getUsername())
                                 .month(month)
                                 .year(year)
                                 .build();
        var flightsReadDto = flightService.findAllByCrewAndMonth(filter);
        model.addAttribute("month", month);
        model.addAttribute("year", year);
        model.addAttribute("flights", flightsReadDto.getFlights());
        model.addAttribute("flightTime", flightsReadDto.getFlightTime());

        return "user/monthly";
    }

    @GetMapping("/statistics")
    public String yearStatistics(@AuthenticationPrincipal UserDetails userDetails,
                                 Model model,
                                 @RequestParam(value = "year", required = false) Integer year) {
        if (year == null) {
            year = LocalDate.now()
                            .getYear();
        }
        var filter = FlightFilter.builder()
                                 .crewEmail(userDetails.getUsername())
                                 .year(year)
                                 .build();
        model.addAttribute("year", year);
        model.addAttribute("statisticsDto", flightService.findStatisticsByCrewAndYear(filter));

        return "user/statistics";
    }
}
