package com.makhalin.springboot_homework.http.controller;

import com.makhalin.springboot_homework.dto.AirportCreateEditDto;
import com.makhalin.springboot_homework.dto.AirportFilter;
import com.makhalin.springboot_homework.dto.PageResponse;
import com.makhalin.springboot_homework.service.AirportService;
import com.makhalin.springboot_homework.service.CityService;
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
@RequestMapping("/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;
    private final CityService cityService;

    @GetMapping
    public String findAll(Model model, AirportFilter filter, Pageable pageable) {
        var page = airportService.findAll(filter, pageable);
        model.addAttribute("airports", PageResponse.of(page));
        model.addAttribute("filter", filter);

        return "airport/airports";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("airport", airportService.findById(id));
        model.addAttribute("cities", cityService.findAll());

        return "airport/airport";
    }

    @GetMapping("/create")
    public String create(Model model, AirportCreateEditDto airport) {
        model.addAttribute("airport", airport);
        model.addAttribute("cities", cityService.findAll());

        return "airport/create";
    }

    @PostMapping
    public String create(@Valid AirportCreateEditDto airport) {
        return "redirect:/airports/" + airportService.create(airport)
                                                     .getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, @Valid AirportCreateEditDto airport) {
        airportService.update(id, airport);

        return "redirect:/airports/{id}";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        airportService.delete(id);

        return "redirect:/airports";
    }
}
