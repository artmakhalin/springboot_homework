package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends
        JpaRepository<City, Integer>,
        FilterCityRepository {
}
