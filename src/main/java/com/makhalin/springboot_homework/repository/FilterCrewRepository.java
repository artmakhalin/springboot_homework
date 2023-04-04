package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.dto.CrewFilter;
import com.makhalin.springboot_homework.entity.Crew;

import java.util.List;

public interface FilterCrewRepository {

    List<Crew> findByAircraftAndEmploymentDate(CrewFilter filter);
}
