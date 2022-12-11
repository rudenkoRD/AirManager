package com.rrd.airmanager.repositories;

import com.rrd.airmanager.models.plane.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PlanesRepository extends JpaRepository<Plane, Integer> {
    @Query(
            value = "select * from planes p " +
                    "left join (" +
                    "select plane_id flying_plane_id from flights flight " +
                    "where start_time < now() AND end_time > now()" +
                    "group by plane_id" +
                    ") flighs on p.id = flying_plane_id " +
                    "where " +
                    "case " +
                    "   when :flightStatus = 'IN_FLIGHT' then flying_plane_id is not null " +
                    "   when :flightStatus = 'ON_GROUND' then flying_plane_id is null " +
                    "   when :flightStatus is null then true " +
                    "end AND case" +
                    "   when :planeType IS NULL then true " +
                    "   when :planeType IS NOT NULL then p.type = :planeType " +
                    "end",
            nativeQuery = true
    )
    List<Plane> findByFlightStatusAndType(
            @Param("flightStatus") String status,
            @Param("planeType") String type
    );
}
