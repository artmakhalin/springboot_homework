package com.makhalin.springboot_homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

@Data
@Builder
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"flight", "crew"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flight_crew")
public class FlightCrew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Crew crew;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassOfService classOfService;

    @Column(nullable = false)
    private Boolean isTurnaround;

    @Column(nullable = false)
    private Boolean isPassenger;

    public void setCrew(Crew crew) {
        this.crew = crew;
        this.crew.getFlightCrews().add(this);
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
        this.flight.getFlightCrews().add(this);
    }
}
