package com.makhalin.springboot_homework.http.controller;

import com.makhalin.springboot_homework.dto.FlightCreateEditDto;
import com.makhalin.springboot_homework.dto.FlightFilter;
import com.makhalin.springboot_homework.dto.PageResponse;
import com.makhalin.springboot_homework.service.AircraftService;
import com.makhalin.springboot_homework.service.AirportService;
import com.makhalin.springboot_homework.service.FlightCrewService;
import com.makhalin.springboot_homework.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;
    private final AirportService airportService;
    private final AircraftService aircraftService;
    private final FlightCrewService flightCrewService;

    @GetMapping
    public String findAll(Model model, FlightFilter filter, Pageable pageable) {
        var page = flightService.findAll(filter, pageable);
        model.addAttribute("flights", PageResponse.of(page));
        model.addAttribute("filter", filter);

        return "flight/flights";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("flight", flightService.findById(id));
        model.addAttribute("airports", airportService.findAll());
        model.addAttribute("aircraftList", aircraftService.findAll());
        model.addAttribute("flightCrewList", flightCrewService.findAllByFlightId(id));

        return "flight/flight";
    }

    @GetMapping("/create")
    public String create(Model model, FlightCreateEditDto flight) {
        model.addAttribute("flight", flight);
        model.addAttribute("airports", airportService.findAll());
        model.addAttribute("aircraftList", aircraftService.findAll());

        return "flight/create";
    }

    @PostMapping
    public String create(@Valid FlightCreateEditDto flight) {
        return "redirect:/flights/" + flightService.create(flight).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @Valid FlightCreateEditDto flight) {
        flightService.update(id, flight);

        return "redirect:/flights/{id}";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        flightService.delete(id);

        return "redirect:/flights";
    }
}
