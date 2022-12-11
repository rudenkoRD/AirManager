package com.rrd.airmanager.services.airports;

import com.rrd.airmanager.models.airport.Airport;
import com.rrd.airmanager.repositories.AirportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportsAppService implements AirportsService {
    private final AirportsRepository airportsRepository;

    @Autowired
    public AirportsAppService(AirportsRepository airportsRepository) {
        this.airportsRepository = airportsRepository;
    }

    @Override
    public Airport save(Airport entity) {
        return airportsRepository.save(entity);
    }

    @Override
    public List<Airport> findAll() {
        return airportsRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<Airport> entities) {
        airportsRepository.deleteAllInBatch(entities);
    }
}
