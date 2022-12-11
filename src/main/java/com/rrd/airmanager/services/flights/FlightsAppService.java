package com.rrd.airmanager.services.flights;

import com.rrd.airmanager.models.flight.Flight;
import com.rrd.airmanager.models.flight.FlightStatus;
import com.rrd.airmanager.repositories.FlightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightsAppService implements FlightsService {
    private final FlightsRepository flightsRepository;

    @Autowired
    public FlightsAppService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    @Override
    public Flight save(Flight entity) {
        return flightsRepository.save(entity);
    }

    @Override
    public List<Flight> findAll() {
        return flightsRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<Flight> entities) {
        flightsRepository.deleteAllInBatch(entities);
    }

    @Override
    public List<Flight> findByStatus(FlightStatus status) {
        return flightsRepository.findByStatus(status);
    }
}
