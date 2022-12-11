package com.rrd.airmanager.repositories;

import com.rrd.airmanager.models.airport.Airport;
import com.rrd.airmanager.models.flight.Flight;
import com.rrd.airmanager.models.flight.FlightStatus;
import com.rrd.airmanager.models.pilot.Pilot;
import com.rrd.airmanager.models.plane.Plane;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FlightsRepositoryTest {
    @Autowired
    private FlightsRepository flightsRepository;

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    public void saveFlightSuccessTest() {
        Flight flight = Flight.builder()
                .startAirport(Airport.builder().id(1).build())
                .arrivalAirport(Airport.builder().id(2).build())
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .firstPilot(Pilot.builder().id(1).build())
                .secondPilot(Pilot.builder().id(2).build())
                .plane(Plane.builder().id(1).build())
                .build();

        flightsRepository.save(flight);

        Assertions.assertThat(flight.getId()).isGreaterThan(0);
    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    public void saveFlightWithoutSecondPilotSuccessTest() {
        Flight flight = Flight.builder()
                .startAirport(Airport.builder().id(1).build())
                .arrivalAirport(Airport.builder().id(2).build())
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .firstPilot(Pilot.builder().id(1).build())
                .plane(Plane.builder().id(1).build())
                .build();

        flightsRepository.save(flight);

        Assertions.assertThat(flight.getId()).isGreaterThan(0);
    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    public void saveFlightWithInvalidStartAirportFailsTest() {
        Flight flight = Flight.builder()
                .startAirport(Airport.builder().id(-1).build())
                .arrivalAirport(Airport.builder().id(2).build())
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .firstPilot(Pilot.builder().id(1).build())
                .plane(Plane.builder().id(1).build())
                .build();

        Assertions.assertThatThrownBy(() -> {
            flightsRepository.save(flight);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    public void saveFlightWithInvalidArrivalAirportFailsTest() {
        Flight flight = Flight.builder()
                .startAirport(Airport.builder().id(1).build())
                .arrivalAirport(Airport.builder().id(-2).build())
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .firstPilot(Pilot.builder().id(1).build())
                .plane(Plane.builder().id(1).build())
                .build();

        Assertions.assertThatThrownBy(() -> {
            flightsRepository.save(flight);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    public void saveFlightWithInvalidFirstPilotFailsTest() {
        Flight flight = Flight.builder()
                .startAirport(Airport.builder().id(1).build())
                .arrivalAirport(Airport.builder().id(2).build())
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .firstPilot(Pilot.builder().id(-1).build())
                .plane(Plane.builder().id(1).build())
                .build();

        Assertions.assertThatThrownBy(() -> {
            flightsRepository.save(flight);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    public void saveFlightWithInvalidSecondPilotFailsTest() {
        Flight flight = Flight.builder()
                .startAirport(Airport.builder().id(1).build())
                .arrivalAirport(Airport.builder().id(2).build())
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .firstPilot(Pilot.builder().id(1).build())
                .firstPilot(Pilot.builder().id(-1).build())
                .plane(Plane.builder().id(1).build())
                .build();

        Assertions.assertThatThrownBy(() -> {
            flightsRepository.save(flight);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    public void saveFlightWithInvalidPlaneFailsTest() {
        Flight flight = Flight.builder()
                .startAirport(Airport.builder().id(1).build())
                .arrivalAirport(Airport.builder().id(2).build())
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .firstPilot(Pilot.builder().id(1).build())
                .firstPilot(Pilot.builder().id(1).build())
                .plane(Plane.builder().id(-1).build())
                .build();

        Assertions.assertThatThrownBy(() -> {
            flightsRepository.save(flight);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    @Sql("classpath:/db_flights_data.sql")
    public void findAllFlightsTest() {
        List<Flight> flights = flightsRepository.findAll();

        Assertions.assertThat(
                flights.stream().mapToInt(Flight::getId).toArray()
        ).isEqualTo(new int[]{1, 2, 3});
    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    @Sql("classpath:/db_flights_data.sql")
    public void deleteAllInBatchFlightsTest() {
        List<Flight> flights = flightsRepository.findAll();

        flightsRepository.deleteAllInBatch(flights.subList(0, 2));

        List<Flight> leftFlights = flightsRepository.findAll();

        Assertions.assertThat(leftFlights.size()).isEqualTo(1);
        Assertions.assertThat(leftFlights.get(0).getId()).isEqualTo(3);
    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    @Sql("classpath:/db_flights_data.sql")
    public void findByStatusInProgressTest() {
        List<Flight> flights = flightsRepository.findByStatus(FlightStatus.IN_PROGRESS);

        Assertions.assertThat(flights.size()).isEqualTo(1);
        Assertions.assertThat(flights.get(0).getId()).isEqualTo(1);

    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    @Sql("classpath:/db_flights_data.sql")
    public void findByStatusUpcomingTest() {
        List<Flight> flights = flightsRepository.findByStatus(FlightStatus.UPCOMING);

        Assertions.assertThat(flights.size()).isEqualTo(1);
        Assertions.assertThat(flights.get(0).getId()).isEqualTo(2);

    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    @Sql("classpath:/db_pilots_data.sql")
    @Sql("classpath:/db_planes_data.sql")
    @Sql("classpath:/db_flights_data.sql")
    public void findByStatusCompletedTest() {
        List<Flight> flights = flightsRepository.findByStatus(FlightStatus.COMPLETED);

        Assertions.assertThat(flights.size()).isEqualTo(1);
        Assertions.assertThat(flights.get(0).getId()).isEqualTo(3);

    }
}
