package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CityCreateEditDto;
import com.makhalin.springboot_homework.entity.City;
import com.makhalin.springboot_homework.entity.Country;
import com.makhalin.springboot_homework.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CityCreateEditMapper implements Mapper<CityCreateEditDto, City> {

    private final CountryRepository countryRepository;

    @Override
    public City map(CityCreateEditDto object) {
        var city = new City();
        copy(object, city);

        return city;
    }

    @Override
    public City map(CityCreateEditDto fromObject, City toObject) {
        copy(fromObject, toObject);

        return toObject;
    }

    private void copy(CityCreateEditDto object, City city) {
        city.setName(object.getName());
        city.setCountry(getCountry(object.getCountryId()));
    }

    private Country getCountry(Integer countryId) {
        return Optional.ofNullable(countryId)
                .flatMap(countryRepository::findById)
                .orElse(null);
    }
}
