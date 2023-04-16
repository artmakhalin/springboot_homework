package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.dto.CrewFilter;
import com.makhalin.springboot_homework.entity.Crew;
import com.makhalin.springboot_homework.entity.CrewAircraft;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.makhalin.springboot_homework.entity.QAircraft.aircraft;
import static com.makhalin.springboot_homework.entity.QCrew.crew;
import static com.makhalin.springboot_homework.entity.QCrewAircraft.crewAircraft;
import static org.hibernate.graph.GraphSemantic.LOAD;

public class FilterCrewRepositoryImpl implements FilterCrewRepository {

    @Autowired
    private EntityManager entityManager;

    /**
     * Выводит всех БП, которые начали работать после указанной даты, с допуском на указанное ВС
     */

    @Override
    public List<Crew> findByAircraftAndEmploymentDate(CrewFilter filter) {
        var crewGraph = entityManager.createEntityGraph(Crew.class);
        crewGraph.addAttributeNodes("crewAircraft");
        var crewAircraftSubgraph = crewGraph.addSubgraph("crewAircraft", CrewAircraft.class);
        crewAircraftSubgraph.addAttributeNodes("aircraft");

        var predicate = QPredicate.builder()
                                  .add(LocalDate.ofYearDay(filter.getStartYear(), 1), crew.employmentDate::after)
                                  .add(filter.getAircraftModel(), crewAircraft.aircraft.model::equalsIgnoreCase)
                                  .buildAnd();

        return new JPAQuery<Crew>(entityManager)
                .select(crew)
                .from(crew)
                .join(crew.crewAircraft, crewAircraft)
                .join(crewAircraft.aircraft, aircraft)
                .where(predicate)
                .setHint(LOAD.getJpaHintName(), crewGraph)
                .fetch();
    }
}
