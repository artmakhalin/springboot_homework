package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface CityRepository extends
        JpaRepository<City, Integer>,
        FilterCityRepository,
        QuerydslPredicateExecutor<City> {

    List<City> findAllByCountryId(Integer countryId);
}
