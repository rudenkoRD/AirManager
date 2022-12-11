package com.rrd.airmanager.services;

import com.rrd.airmanager.models.airport.Airport;
import com.rrd.airmanager.repositories.AirportsRepository;
import com.rrd.airmanager.services.airports.AirportsAppService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class AirportsAppServiceTest {
    private final Airport airport = Airport.builder()
            .location("test")
            .maxCapacity(10)
            .build();
    List<Airport> airports = Collections.singletonList(airport);

    @InjectMocks
    AirportsAppService airportsService;

    @Mock
    AirportsRepository airportsRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllAirportsTest() {
        when(airportsRepository.findAll()).thenReturn(airports);

        List<Airport> foundAirports = airportsService.findAll();

        Assertions.assertThat(foundAirports).isEqualTo(airports);
    }

    @Test
    public void saveAirportTest() {
        airportsService.save(airport);

        verify(airportsRepository, times(1)).save(airport);
    }

    @Test
    public void deleteAllInBatchAirportsTest() {
        airportsService.deleteInBatch(airports);

        verify(airportsRepository, times(1)).deleteAllInBatch(airports);
    }
}
