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
import java.time.LocalDate;

@Data
@Builder
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"crew", "aircraft"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "crew_aircraft")
public class CrewAircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate permitDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Crew crew;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Aircraft aircraft;

    public void setCrew(Crew crew) {
        this.crew = crew;
        this.crew.getCrewAircraft().add(this);
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
        this.aircraft.getCrewAircraft().add(this);
    }
}
