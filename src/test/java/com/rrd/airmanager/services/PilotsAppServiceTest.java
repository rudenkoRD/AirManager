package com.rrd.airmanager.services;

import com.rrd.airmanager.models.pilot.Pilot;
import com.rrd.airmanager.models.pilot.PilotType;
import com.rrd.airmanager.repositories.PilotsRepository;
import com.rrd.airmanager.services.pilots.PilotsAppService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class PilotsAppServiceTest {
    private final Pilot pilot = Pilot.builder()
            .pilotType(PilotType.CAPTAIN)
            .name("Name")
            .birthday(LocalDate.now())
            .flyingHours(10.0)
            .salary(1)
            .build();

    List<Pilot> pilots = Collections.singletonList(pilot);

    @InjectMocks
    PilotsAppService pilotsAppService;

    @Mock
    PilotsRepository pilotsRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void savePilotTest() {
        pilotsAppService.save(pilot);

        verify(pilotsRepository, times(1)).save(pilot);
    }

    @Test
    public void findAllPilotsTest() {
        when(pilotsRepository.findAll()).thenReturn(pilots);

        List<Pilot> foundPilots = pilotsAppService.findAll();

        Assertions.assertThat(foundPilots).isEqualTo(pilots);
    }

    @Test
    public void deleteInBatchPilotTest() {
        pilotsAppService.deleteInBatch(pilots);

        verify(pilotsRepository, times(1)).deleteAllInBatch(pilots);
    }

    @Test
    public void findPilotByTypeTest() {
        PilotType type = PilotType.CAPTAIN;

        pilotsAppService.findByType(type);

        verify(pilotsRepository, times(1)).findPilotsByPilotType(type);
    }
}
