package com.rrd.airmanager.models.pilot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pilots")
public class Pilot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "salary", nullable = false)
    private int salary;

    @Column(name = "flying_hours", nullable = false)
    private double flyingHours;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PilotType pilotType;

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }
}
