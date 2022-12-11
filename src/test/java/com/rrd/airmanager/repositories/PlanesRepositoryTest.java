package com.rrd.airmanager.repositories;

import com.rrd.airmanager.models.plane.Plane;
import com.rrd.airmanager.models.plane.PlaneFlightStatus;
import com.rrd.airmanager.models.plane.PlaneType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlanesRepositoryTest {
    @Autowired
    private PlanesRepository planesRepository;

    @Test
    public void savePlaneSuccessTest() {
        Plane plane = Plane.builder()
                .name("Test")
                .capacity(1)
                .fuelConsumption(1.0)
                .maxFlightDistance(1.0)
                .type(PlaneType.CARGO)
                .build();

        planesRepository.save(plane);

        Assertions.assertThat(plane.getId()).isGreaterThan(0);
    }

    @Test
    @Sql("classpath:/db_planes_data.sql")
    public void findAllPilotsTest() {
        List<Plane> planes = planesRepository.findAll();

        Assertions.assertThat(
                planes.stream().mapToInt(Plane::getId).toArray()
        ).isEqualTo(new int[]{1, 2, 3});
    }

    @Test
    @Sql("classpath:/db_planes_data.sql")
    public void deleteAllInBatchPilotsTest() {
        List<Plane> planes = planesRepository.findAll();

        planesRepository.deleteAllInBatch(planes.subList(0, 2));

        List<Plane> leftPlanes = planesRepository.findAll();

        Assertions.assertThat(leftPlanes.size()).isEqualTo(1);
        Assertions.assertThat(leftPlanes.get(0).getId()).isEqualTo(3);
    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    @Sql("classpath:/db_flights_data.sql")
    public void findPlanesByFlightStatusAndTypeFindsAllTest() {
        List<Plane> planes = planesRepository.findByFlightStatusAndType(null, null);

        Assertions.assertThat(
                planes.stream().mapToInt(Plane::getId).toArray()
        ).isEqualTo(new int[]{1, 2, 3});
    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    @Sql("classpath:/db_flights_data.sql")
    public void findPlanesByAnyFlightStatusAndPrivateTypeTest() {
        List<Plane> planes = planesRepository.findByFlightStatusAndType(null, PlaneType.PRIVATE.name());

        Assertions.assertThat(planes.size()).isEqualTo(1);
        Assertions.assertThat(planes.get(0).getId()).isEqualTo(3);
    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    @Sql("classpath:/db_flights_data.sql")
    public void findPlanesByAnyFlightStatusAndCargoTypeTest() {
        List<Plane> planes = planesRepository.findByFlightStatusAndType(null, PlaneType.CARGO.name());

        Assertions.assertThat(planes.size()).isEqualTo(1);
        Assertions.assertThat(planes.get(0).getId()).isEqualTo(1);
    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    @Sql("classpath:/db_flights_data.sql")
    public void findPlanesByAnyFlightStatusAndCommercialTypeTest() {
        List<Plane> planes = planesRepository.findByFlightStatusAndType(null, PlaneType.COMMERCIAL.name());

        Assertions.assertThat(planes.size()).isEqualTo(1);
        Assertions.assertThat(planes.get(0).getId()).isEqualTo(2);
    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    @Sql("classpath:/db_flights_data.sql")
    public void findPlanesByOnGroundFlightStatusAndCOMMERCIALTypeTest() {
        List<Plane> planes = planesRepository.findByFlightStatusAndType(
                PlaneFlightStatus.ON_GROUND.name(), PlaneType.COMMERCIAL.name()
        );

        Assertions.assertThat(planes.size()).isEqualTo(1);
        Assertions.assertThat(planes.get(0).getId()).isEqualTo(2);
    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    @Sql("classpath:/db_flights_data.sql")
    public void findPlanesByInFlightFlightStatusAndCargoTypeTest() {
        List<Plane> planes = planesRepository.findByFlightStatusAndType(
                PlaneFlightStatus.IN_FLIGHT.name(), PlaneType.CARGO.name()
        );

        Assertions.assertThat(planes.size()).isEqualTo(1);
        Assertions.assertThat(planes.get(0).getId()).isEqualTo(1);
    }
}
