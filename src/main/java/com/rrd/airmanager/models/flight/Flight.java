package com.rrd.airmanager.models.flight;

import com.rrd.airmanager.models.pilot.Pilot;
import com.rrd.airmanager.models.plane.Plane;
import com.rrd.airmanager.models.airport.Airport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @JoinColumn(name = "start_airport_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Airport startAirport;

    @JoinColumn(name = "arrival_airport_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Airport arrivalAirport;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @JoinColumn(name = "first_pilot_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Pilot firstPilot;

    @JoinColumn(name = "second_pilot_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Pilot secondPilot;

    @JoinColumn(name = "plane_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Plane plane;

    @Formula(value = "CASE" +
            "    WHEN start_time < now() AND end_time > now() THEN 'IN_PROGRESS'" +
            "    WHEN end_time < now() THEN 'COMPLETED'" +
            "    else 'UPCOMING'" +
            "END ")
    @Enumerated(EnumType.STRING)
    private FlightStatus status;
}
