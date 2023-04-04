package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FlightRepository extends
        JpaRepository<Flight, Long>,
        FilterFlightRepository,
        QuerydslPredicateExecutor<Flight> {
}
