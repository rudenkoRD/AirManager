package com.rrd.airmanager.services.flights;

import com.rrd.airmanager.models.flight.Flight;
import com.rrd.airmanager.models.flight.FlightStatus;
import com.rrd.airmanager.services.AppService;

import java.util.List;

public interface FlightsService extends AppService<Flight> {
    List<Flight> findByStatus(FlightStatus status);
}
