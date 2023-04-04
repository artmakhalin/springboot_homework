package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, Integer> {
}
