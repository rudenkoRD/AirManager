package com.rrd.airmanager.services.planes;

import com.rrd.airmanager.models.plane.Plane;
import com.rrd.airmanager.repositories.PlanesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanesAppService implements PlanesService {
    private final PlanesRepository planesRepository;

    @Autowired
    public PlanesAppService(PlanesRepository planesRepository) {
        this.planesRepository = planesRepository;
    }

    @Override
    public Plane save(Plane entity) {
        return planesRepository.save(entity);
    }


    @Override
    public List<Plane> findAll() {
        return planesRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<Plane> entities) {
        planesRepository.deleteAllInBatch(entities);
    }

    @Override
    public List<Plane> findByFlightStatusAndType(String status, String type) {
        return planesRepository.findByFlightStatusAndType(status, type);
    }
}
