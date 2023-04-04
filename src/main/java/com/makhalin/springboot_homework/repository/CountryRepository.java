package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
