package com.makhalin.springboot_homework.http.controller;

import com.makhalin.springboot_homework.dto.CountryCreateEditDto;
import com.makhalin.springboot_homework.service.CityService;
import com.makhalin.springboot_homework.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;
    private final CityService cityService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("countries", countryService.findAll());

        return "country/countries";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("country", countryService.findById(id));
        model.addAttribute("cities", cityService.findAllByCountryId(id));

        return "country/country";
    }

    @GetMapping("/create")
    public String create(Model model, CountryCreateEditDto country) {
        model.addAttribute("country", country);

        return "country/create";
    }

    @PostMapping
    public String create(@Valid CountryCreateEditDto country) {
        return "redirect:/countries/" + countryService.create(country)
                                                      .getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, @Valid CountryCreateEditDto country) {
        countryService.update(id, country);

        return "redirect:/countries/{id}";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        countryService.delete(id);

        return "redirect:/countries";
    }
}
