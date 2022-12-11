package com.rrd.airmanager.services.pilots;

import com.rrd.airmanager.models.pilot.Pilot;
import com.rrd.airmanager.models.pilot.PilotType;
import com.rrd.airmanager.repositories.PilotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PilotsAppService implements PilotsService {
    private final PilotsRepository pilotsRepository;

    @Autowired
    public PilotsAppService(PilotsRepository pilotsRepository) {
        this.pilotsRepository = pilotsRepository;
    }

    @Override
    public Pilot save(Pilot entity) {
        return pilotsRepository.save(entity);
    }

    @Override
    public List<Pilot> findAll() {
        return pilotsRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<Pilot> entities) {
        pilotsRepository.deleteAllInBatch(entities);
    }

    @Override
    public List<Pilot> findByType(PilotType type) {
        return pilotsRepository.findPilotsByPilotType(type);
    }
}
