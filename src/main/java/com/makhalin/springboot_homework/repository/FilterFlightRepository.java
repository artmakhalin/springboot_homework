package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.dto.FlightFilter;
import com.makhalin.springboot_homework.entity.Flight;

import java.util.List;
import java.util.Map;

public interface FilterFlightRepository {

    List<Flight> findByCrewAndMonth(FlightFilter filter);

    Map<Integer, Long> findMonthlyFlightTimeStatisticsByCrewAndYear(FlightFilter filter);

    Map<String, Long> findAircraftFlightTimeStatisticsByCrew(String email);
}
