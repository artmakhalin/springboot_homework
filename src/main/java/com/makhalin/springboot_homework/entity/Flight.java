package com.makhalin.springboot_homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(of = {"flightNo", "departureDate"})
@ToString(exclude = {
        "departureAirport",
        "arrivalAirport",
        "transitAirport",
        "aircraft",
        "flightCrews"
})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@OptimisticLocking(type = OptimisticLockType.VERSION)
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(nullable = false)
    private String flightNo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "departure_airport_code")
    private Airport departureAirport;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "arrival_airport_code")
    private Airport arrivalAirport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transit_airport_code")
    private Airport transitAirport;

    @Column(nullable = false)
    private LocalDate departureDate;

    @Column(nullable = false)
    private Long time;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Aircraft aircraft;

    @Builder.Default
    @OneToMany(mappedBy = "flight")
    private List<FlightCrew> flightCrews = new ArrayList<>();
}
