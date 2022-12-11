package com.rrd.airmanager.services;

import com.rrd.airmanager.models.plane.Plane;
import com.rrd.airmanager.models.plane.PlaneFlightStatus;
import com.rrd.airmanager.models.plane.PlaneType;
import com.rrd.airmanager.repositories.PlanesRepository;
import com.rrd.airmanager.services.planes.PlanesAppService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class PlanesAppServiceTest {
    private final Plane plane = Plane.builder()
            .name("Test")
            .capacity(1)
            .fuelConsumption(1.0)
            .maxFlightDistance(1.0)
            .build();

    List<Plane> planes = Collections.singletonList(plane);


    @InjectMocks
    PlanesAppService planesAppService;

    @Mock
    PlanesRepository planesRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void savePlaneTest() {
        planesAppService.save(plane);

        verify(planesRepository, times(1)).save(plane);
    }

    @Test
    public void findAllPlanesTest() {
        when(planesRepository.findAll()).thenReturn(planes);

        List<Plane> foundPlanes = planesAppService.findAll();

        Assertions.assertThat(foundPlanes).isEqualTo(planes);
    }

    @Test
    public void deleteInBatchPlaneTest() {
        planesAppService.deleteInBatch(planes);

        verify(planesRepository, times(1)).deleteAllInBatch(planes);
    }

    @Test
    public void findByFlightStatusAndTypeTest() {
        PlaneFlightStatus status = PlaneFlightStatus.IN_FLIGHT;
        PlaneType type = PlaneType.CARGO;

        planesAppService.findByFlightStatusAndType(status.name(), type.name());

        verify(planesRepository, times(1))
                .findByFlightStatusAndType(status.name(), type.name());
    }

}
