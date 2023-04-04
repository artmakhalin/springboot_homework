package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.FlightCrew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightCrewRepository extends JpaRepository<FlightCrew, Long> {
}
