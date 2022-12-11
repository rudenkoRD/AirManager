package com.rrd.airmanager.repositories;

import com.rrd.airmanager.models.pilot.Pilot;
import com.rrd.airmanager.models.pilot.PilotType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PilotsRepository extends JpaRepository<Pilot, Integer> {

    List<Pilot> findPilotsByPilotType(PilotType type);
}
