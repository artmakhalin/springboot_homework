package com.makhalin.springboot_homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(of = "code")
@ToString(exclude = "city")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "airport")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(columnDefinition = "bpchar", unique = true, nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private City city;

    @Builder.Default
    @OneToMany(mappedBy = "departureAirport")
    private List<Flight> departureFlights = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "arrivalAirport")
    private List<Flight> arrivalFlights = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "transitAirport")
    private List<Flight> transitFlights = new ArrayList<>();
}
