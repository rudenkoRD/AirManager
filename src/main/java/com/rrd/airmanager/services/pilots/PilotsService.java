package com.rrd.airmanager.services.pilots;

import com.rrd.airmanager.models.pilot.Pilot;
import com.rrd.airmanager.models.pilot.PilotType;
import com.rrd.airmanager.services.AppService;

import java.util.List;

public interface PilotsService extends AppService<Pilot> {
    List<Pilot> findByType(PilotType type);
}
