package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.dto.FlightsFilter;
import com.makhalin.springboot_homework.entity.Flight;

import java.util.List;
import java.util.Map;

public interface FilterFlightRepository {

    List<Flight> findByCrewAndMonth(FlightsFilter filter);

    Map<Integer, Long> findMonthlyFlightTimeStatisticsByCrewAndYear(FlightsFilter filter);

    Map<String, Long> findAircraftFlightTimeStatisticsByCrew(String email);
}
