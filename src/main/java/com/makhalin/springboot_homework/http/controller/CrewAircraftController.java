package com.makhalin.springboot_homework.http.controller;

import com.makhalin.springboot_homework.dto.CrewAircraftCreateEditDto;
import com.makhalin.springboot_homework.service.AircraftService;
import com.makhalin.springboot_homework.service.CrewAircraftService;
import com.makhalin.springboot_homework.service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/crewAircraft")
@RequiredArgsConstructor
public class CrewAircraftController {

    private final CrewAircraftService crewAircraftService;
    private final AircraftService aircraftService;
    private final CrewService crewService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("crewAircraft", crewAircraftService.findById(id));
        model.addAttribute("crewList", crewService.findAll());
        model.addAttribute("aircraftList", aircraftService.findAll());

        return "crewAircraft/crewAircraft";
    }

    @GetMapping("/{crewId}/create")
    public String create(@PathVariable("crewId") Integer id, Model model, CrewAircraftCreateEditDto crewAircraft) {
        model.addAttribute("crewAircraft", crewAircraft);
        model.addAttribute("crew", crewService.findById(id));
        model.addAttribute("aircraftList", aircraftService.findAll());

        return "crewAircraft/create";
    }

    @PostMapping
    public String create(@Valid CrewAircraftCreateEditDto crewAircraft) {
        return "redirect:/crewAircraft/" + crewAircraftService.create(crewAircraft)
                                                             .getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @Valid CrewAircraftCreateEditDto crewAircraft) {
        crewAircraftService.update(id, crewAircraft);

        return "redirect:/crew/" + crewAircraft.getCrewId();
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        crewAircraftService.delete(id);

        return "redirect:/crew";
    }
}
