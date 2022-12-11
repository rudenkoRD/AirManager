package com.rrd.airmanager.services.planes;

import com.rrd.airmanager.models.plane.Plane;
import com.rrd.airmanager.services.AppService;

import java.util.List;

public interface PlanesService extends AppService<Plane> {
    List<Plane> findByFlightStatusAndType(String status, String type);
}
