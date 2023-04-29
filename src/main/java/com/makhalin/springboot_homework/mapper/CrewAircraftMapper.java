package com.makhalin.springboot_homework.mapper;

import com.makhalin.springboot_homework.dto.CrewAircraftCreateEditDto;
import com.makhalin.springboot_homework.dto.CrewAircraftReadDto;
import com.makhalin.springboot_homework.entity.CrewAircraft;
import com.makhalin.springboot_homework.repository.AircraftRepository;
import com.makhalin.springboot_homework.repository.CrewRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {CrewMapper.class, AircraftMapper.class})
public abstract class CrewAircraftMapper {

    @Autowired
    protected CrewRepository crewRepository;

    @Autowired
    protected AircraftRepository aircraftRepository;

    private static final String GET_CREW = """
                            java(java.util.Optional.ofNullable(object.getCrewId())
                            .flatMap(crewRepository::findById)
                            .orElse(null))
            """;

    private static final String GET_AIRCRAFT = """
                            java(java.util.Optional.ofNullable(object.getAircraftId())
                            .flatMap(aircraftRepository::findById)
                            .orElse(null))
            """;

    public abstract CrewAircraftReadDto mapRead(CrewAircraft object);

    @Mapping(target = "crew", expression = GET_CREW)
    @Mapping(target = "aircraft", expression = GET_AIRCRAFT)
    public abstract CrewAircraft mapCreate(CrewAircraftCreateEditDto object);

    @Mapping(target = "crew", expression = GET_CREW)
    @Mapping(target = "aircraft", expression = GET_AIRCRAFT)
    public abstract CrewAircraft mapUpdate(CrewAircraftCreateEditDto object, @MappingTarget CrewAircraft toObject);
}
