package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.CrewAircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewAircraftRepository extends JpaRepository<CrewAircraft, Long> {
}
