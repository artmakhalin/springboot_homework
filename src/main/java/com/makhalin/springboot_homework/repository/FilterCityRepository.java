package com.makhalin.springboot_homework.repository;

import com.querydsl.core.Tuple;

import java.util.List;

public interface FilterCityRepository {

    List<Tuple> findCityAndCountOfVisitsByCrew(String email);
}
