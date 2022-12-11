package com.rrd.airmanager.repositories;

import com.rrd.airmanager.models.airport.Airport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.assertj.core.api.Assertions;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AirportsRepositoryTest {

    @Autowired
    private AirportsRepository airportsRepository;

    @Test
    public void saveAirportTest() {
        Airport airport = Airport.builder()
                .location("test")
                .maxCapacity(10)
                .build();
        airportsRepository.save(airport);

        Assertions.assertThat(airport.getId()).isGreaterThan(0);
    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    public void findAllAirportsTest() {
        List<Airport> airports = airportsRepository.findAll();

        Assertions.assertThat(
                airports.stream().mapToInt(Airport::getId).toArray()
        ).isEqualTo(new int[]{1, 2, 3});
    }

    @Test
    @Sql("classpath:/db_airports_data.sql")
    public void deleteAllInBatchAirportsTest() {
        List<Airport> airports = airportsRepository.findAll();

        airportsRepository.deleteAllInBatch(airports.subList(0, 2));

        List<Airport> leftAirports = airportsRepository.findAll();

        Assertions.assertThat(leftAirports.size()).isEqualTo(1);
        Assertions.assertThat(leftAirports.get(0).getId()).isEqualTo(3);
    }
}
