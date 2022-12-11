package com.rrd.airmanager.events;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class InfoEventTest {

    @Test
    public void getInfoEventMessageTest() {
        String message = "message";
        InfoEvent event = new InfoEvent(message);

        Assertions.assertThat(event.getMessage()).isEqualTo(message);
    }
}
