package com.rrd.airmanager.models.plane;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "planes")
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PlaneType type;

    @Column(name = "fuel_consumption", nullable = false)
    private double fuelConsumption;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "max_flight_distance", nullable = false)
    private double maxFlightDistance;

    @Column(name = "name", nullable = false)
    private String name;

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }
}
