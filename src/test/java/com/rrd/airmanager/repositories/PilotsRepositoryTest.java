package com.rrd.airmanager.repositories;

import com.rrd.airmanager.models.pilot.Pilot;
import com.rrd.airmanager.models.pilot.PilotType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PilotsRepositoryTest {
    @Autowired
    private PilotsRepository pilotsRepository;

    @Test
    public void savePilotSuccessTest() {
        Pilot pilot = Pilot.builder()
                .name("Test")
                .pilotType(PilotType.CAPTAIN)
                .salary(1)
                .flyingHours(1)
                .birthday(LocalDate.now())
                .build();

        pilotsRepository.save(pilot);

        Assertions.assertThat(pilot.getId()).isGreaterThan(0);
    }

    @Test
    @Sql("classpath:/db_pilots_data.sql")
    public void findAllPilotsTest() {
        List<Pilot> pilots = pilotsRepository.findAll();

        Assertions.assertThat(
                pilots.stream().mapToInt(Pilot::getId).toArray()
        ).isEqualTo(new int[]{1, 2, 3});
    }

    @Test
    @Sql("classpath:/db_pilots_data.sql")
    public void deleteAllInBatchPilotsTest() {
        List<Pilot> pilots = pilotsRepository.findAll();

        pilotsRepository.deleteAllInBatch(pilots.subList(0, 2));

        List<Pilot> leftPilots = pilotsRepository.findAll();

        Assertions.assertThat(leftPilots.size()).isEqualTo(1);
        Assertions.assertThat(leftPilots.get(0).getId()).isEqualTo(3);
    }

    @Test
    @Sql("classpath:/db_pilots_data.sql")
    public void findPilotByTypeCaptainTest() {
        List<Pilot> pilots = pilotsRepository.findPilotsByPilotType(PilotType.CAPTAIN);

        Assertions.assertThat(pilots.size()).isEqualTo(2);
        Assertions.assertThat(pilots.stream().mapToInt(Pilot::getId).toArray()).isEqualTo(new int[]{1, 2});
    }

    @Test
    @Sql("classpath:/db_pilots_data.sql")
    public void findPilotByTypeSecondPilotTest() {
        List<Pilot> pilots = pilotsRepository.findPilotsByPilotType(PilotType.SECOND_PILOT);

        Assertions.assertThat(pilots.size()).isEqualTo(1);
        Assertions.assertThat(pilots.get(0).getId()).isEqualTo(3);
    }
}
