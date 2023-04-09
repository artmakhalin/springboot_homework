package com.makhalin.springboot_homework.http.controller;

import com.makhalin.springboot_homework.dto.CountryCreateEditDto;
import com.makhalin.springboot_homework.service.CityService;
import com.makhalin.springboot_homework.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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
        return countryService.findById(id)
                             .map(country -> {
                                 model.addAttribute("country", country);
                                 model.addAttribute("cities", cityService.findAllByCountryId(id));

                                 return "country/country";
                             })
                             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/create")
    public String create(Model model, CountryCreateEditDto country) {
        model.addAttribute("country", country);

        return "country/create";
    }

    @PostMapping
    public String create(@Validated CountryCreateEditDto country) {
        return "redirect:/countries/" + countryService.create(country)
                                                      .getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, @Validated CountryCreateEditDto country) {
        return countryService.update(id, country)
                             .map(it -> "redirect:/countries/{id}")
                             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!countryService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return "redirect:/countries";
    }
}
