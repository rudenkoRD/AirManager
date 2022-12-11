package com.rrd.airmanager.services;

import com.rrd.airmanager.models.airport.Airport;
import com.rrd.airmanager.models.flight.Flight;
import com.rrd.airmanager.models.flight.FlightStatus;
import com.rrd.airmanager.models.pilot.Pilot;
import com.rrd.airmanager.models.pilot.PilotType;
import com.rrd.airmanager.models.plane.Plane;
import com.rrd.airmanager.models.plane.PlaneType;
import com.rrd.airmanager.repositories.FlightsRepository;
import com.rrd.airmanager.services.flights.FlightsAppService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class FlightsAppServiceTest {
    private final Airport airport = Airport.builder()
            .location("test")
            .maxCapacity(10)
            .build();

    private final Pilot pilot = Pilot.builder()
            .pilotType(PilotType.CAPTAIN)
            .name("Name")
            .birthday(LocalDate.now())
            .flyingHours(10.0)
            .salary(1)
            .build();

    private final Plane plane = Plane.builder()
            .name("Test")
            .capacity(1)
            .fuelConsumption(1.0)
            .maxFlightDistance(1.0)
            .type(PlaneType.CARGO)
            .build();

    Flight flight = Flight.builder()
            .startAirport(airport)
            .arrivalAirport(airport)
            .status(FlightStatus.COMPLETED)
            .startTime(LocalDateTime.now())
            .endTime(LocalDateTime.now())
            .firstPilot(pilot)
            .plane(plane)
            .build();

    List<Flight> flights = Collections.singletonList(flight);

    @InjectMocks
    FlightsAppService flightsAppService;

    @Mock
    FlightsRepository flightsRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveFlightTest() {
        flightsAppService.save(flight);

        verify(flightsRepository, times(1)).save(flight);
    }

    @Test
    public void findAllFlightsTest() {
        when(flightsRepository.findAll()).thenReturn(flights);

        List<Flight> foundFlights = flightsAppService.findAll();

        Assertions.assertThat(foundFlights).isEqualTo(flights);
    }

    @Test
    public void deleteInBatchFlightsTest() {
        flightsAppService.deleteInBatch(flights);

        verify(flightsRepository, times(1)).deleteAllInBatch(flights);
    }

    @Test
    public void findByStatusTest() {
        FlightStatus status = FlightStatus.COMPLETED;

        flightsAppService.findByStatus(status);

        verify(flightsRepository, times(1)).findByStatus(status);
    }
}
