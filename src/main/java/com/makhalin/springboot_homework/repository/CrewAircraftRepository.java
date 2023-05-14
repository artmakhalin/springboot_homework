package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.CrewAircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CrewAircraftRepository extends JpaRepository<CrewAircraft, Long> {

    List<CrewAircraft> findAllByCrewId(Integer id);
    Optional<CrewAircraft> findByCrewIdAndAircraftId(Integer crewId, Integer aircraftId);
}
