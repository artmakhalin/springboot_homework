package com.makhalin.springboot_homework.http.controller;

import com.makhalin.springboot_homework.dto.FlightCrewCreateEditDto;
import com.makhalin.springboot_homework.entity.ClassOfService;
import com.makhalin.springboot_homework.service.CrewService;
import com.makhalin.springboot_homework.service.FlightCrewService;
import com.makhalin.springboot_homework.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/flightCrew")
@RequiredArgsConstructor
public class FlightCrewController {

    private final FlightCrewService flightCrewService;
    private final CrewService crewService;
    private final FlightService flightService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("flightCrew", flightCrewService.findById(id));
        model.addAttribute("crewList", crewService.findAll());
        model.addAttribute("classesOfService", ClassOfService.values());

        return "flightCrew/flightCrew";
    }

    @GetMapping("/{flightId}/create")
    public String create(@PathVariable("flightId") Long id, Model model, FlightCrewCreateEditDto flightCrew) {
        model.addAttribute("flightCrew", flightCrew);
        model.addAttribute("flight",flightService.findById(id));
        model.addAttribute("crewList", crewService.findAll());
        model.addAttribute("classesOfService", ClassOfService.values());

        return "flightCrew/create";
    }

    @PostMapping
    public String create(@Valid FlightCrewCreateEditDto flightCrew) {
        return "redirect:/flightCrew/" + flightCrewService.create(flightCrew).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @Valid FlightCrewCreateEditDto flightCrew) {
        flightCrewService.update(id, flightCrew);

        return "redirect:/flights/" + flightCrew.getFlightId();
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        flightCrewService.delete(id);

        return "redirect:/flights";
    }
}
