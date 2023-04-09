package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CountryCreateEditDto;
import com.makhalin.springboot_homework.entity.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryCreateEditMapper implements Mapper<CountryCreateEditDto, Country> {

    @Override
    public Country map(CountryCreateEditDto object) {
        var country = new Country();
        copy(object, country);

        return country;
    }

    @Override
    public Country map(CountryCreateEditDto fromObject, Country toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(CountryCreateEditDto object, Country country) {
        country.setName(object.getName());
    }
}
