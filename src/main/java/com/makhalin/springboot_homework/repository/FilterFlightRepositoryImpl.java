package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.dto.FlightsFilter;
import com.makhalin.springboot_homework.entity.*;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.makhalin.springboot_homework.entity.QCrew.crew;
import static com.makhalin.springboot_homework.entity.QFlight.flight;
import static com.makhalin.springboot_homework.entity.QFlightCrew.flightCrew;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.sum;
import static org.hibernate.graph.GraphSemantic.LOAD;

public class FilterFlightRepositoryImpl implements FilterFlightRepository {

    @Autowired
    private EntityManager entityManager;

    /**
     * Находит все рейсы указанного БП за конкретный месяц
     */

    @Override
    public List<Flight> findByCrewAndMonth(FlightsFilter filter) {
        var flightGraph = entityManager.createEntityGraph(Flight.class);
        flightGraph.addAttributeNodes("flightCrews", "time", "departureDate");
        var flightCrewsSubgraph = flightGraph.addSubgraph("flightCrews", FlightCrew.class);
        flightCrewsSubgraph.addAttributeNodes("crew");

        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Flight.class);
        var flight = criteria.from(Flight.class);
        var flightCrews = flight.join(Flight_.flightCrews);
        var crew = flightCrews.join(FlightCrew_.crew);

        var predicates = CPredicate.builder()
                                   .add(filter.getCrewEmail(), email -> cb.equal(crew.get(Crew_.email), email))
                                   .add(LocalDate.of(filter.getYear(), filter.getMonth(), 1),
                                           startMonth -> cb.between(
                                                   flight.get(Flight_.departureDate),
                                                   startMonth,
                                                   startMonth.plusDays(startMonth.lengthOfMonth())
                                           ))
                                   .getPredicateArray();

        criteria.select(flight)
                .where(predicates);

        return entityManager.createQuery(criteria)
                            .setHint(LOAD.getJpaHintName(), flightGraph)
                            .getResultList();
    }

    /**
     * Выводит суммарное время налета за месяц и месяц указанного БП за конкретный год
     */

    @Override
    public Map<Integer, Long> findMonthlyFlightTimeStatisticsByCrewAndYear(FlightsFilter filter) {
        var predicate = QPredicate.builder()
                                  .add(filter.getCrewEmail().toUpperCase(), crew.email.toUpperCase()::eq)
                                  .add(filter.getYear(),
                                          year -> flight.departureDate.between(
                                                  LocalDate.of(year, 1, 1),
                                                  LocalDate.of(year, 12, 31)
                                          )
                                  )
                                  .buildAnd();

        return new JPAQuery<>(entityManager)
                .from(flight)
                .join(flight.flightCrews, flightCrew)
                .join(flightCrew.crew, crew)
                .where(predicate)
                .orderBy(flight.departureDate.month()
                                             .asc())
                .transform(groupBy(flight.departureDate.month())
                        .as(sum(flight.time)));
    }

    /**
     * Выводит суммарное время налета по типам ВС для указанного БП
     */

    @Override
    public Map<String, Long> findAircraftFlightTimeStatisticsByCrew(String email) {
        return new JPAQuery<>(entityManager)
                .from(flight)
                .join(flight.flightCrews, flightCrew)
                .join(flightCrew.crew, crew)
                .where(crew.email.toUpperCase()
                                 .eq(email.toUpperCase()))
                .transform(groupBy(flight.aircraft.model).as(sum(flight.time)));
    }
}
