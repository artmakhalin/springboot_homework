package com.makhalin.springboot_homework.http.controller;

import com.makhalin.springboot_homework.dto.CityCreateEditDto;
import com.makhalin.springboot_homework.dto.CityFilter;
import com.makhalin.springboot_homework.dto.PageResponse;
import com.makhalin.springboot_homework.service.CityService;
import com.makhalin.springboot_homework.service.CountryService;
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
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;
    private final CountryService countryService;

    @GetMapping
    public String findAll(Model model, CityFilter filter, Pageable pageable) {
        var page = cityService.findAll(filter, pageable);
        model.addAttribute("cities", PageResponse.of(page));
        model.addAttribute("filter", filter);

        return "city/cities";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("city", cityService.findById(id));
        model.addAttribute("countries", countryService.findAll());

        return "city/city";
    }

    @GetMapping("/create")
    public String create(Model model, CityCreateEditDto city) {
        model.addAttribute("city", city);
        model.addAttribute("countries", countryService.findAll());

        return "city/create";
    }

    @PostMapping
    public String create(@Valid CityCreateEditDto city) {
        return "redirect:/cities/" + cityService.create(city)
                                                .getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, @Valid CityCreateEditDto city) {
        cityService.update(id, city);

        return "redirect:/cities/{id}";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        cityService.delete(id);

        return "redirect:/cities";
    }
}
