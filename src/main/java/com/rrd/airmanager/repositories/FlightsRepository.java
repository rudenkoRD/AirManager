package com.rrd.airmanager.repositories;

import com.rrd.airmanager.models.flight.Flight;
import com.rrd.airmanager.models.flight.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightsRepository extends JpaRepository<Flight, Integer> {
    List<Flight> findByStatus(FlightStatus status);
}
