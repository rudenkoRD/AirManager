package com.rrd.airmanager.repositories;

import com.rrd.airmanager.models.airport.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportsRepository extends JpaRepository<Airport, Integer> {

}
