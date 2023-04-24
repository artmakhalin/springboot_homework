package com.makhalin.springboot_homework.http.controller;

import com.makhalin.springboot_homework.dto.AircraftCreateEditDto;
import com.makhalin.springboot_homework.service.AircraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/aircraft")
@RequiredArgsConstructor
public class AircraftController {

    private final AircraftService aircraftService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("aircraftList", aircraftService.findAll());

        return "aircraft/aircraftList";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("aircraft", aircraftService.findById(id));

        return "aircraft/aircraft";
    }

    @GetMapping("/create")
    public String create(Model model, AircraftCreateEditDto aircraft) {
        model.addAttribute("aircraft", aircraft);

        return "aircraft/create";
    }

    @PostMapping
    public String create(@Valid AircraftCreateEditDto aircraft) {
        return "redirect:/aircraft/" + aircraftService.create(aircraft).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, @Valid AircraftCreateEditDto aircraft) {
        aircraftService.update(id, aircraft);

        return "redirect:/aircraft/{id}";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        aircraftService.delete(id);

        return "redirect:/aircraft";
    }
}
