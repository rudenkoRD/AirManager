package com.rrd.airmanager.events;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ErrorEventTest {
    private final String message = "error message";
    private final Throwable error = new Exception();
    private final ErrorEvent event = new ErrorEvent(message, error);

    @Test
    public void getErrorEventMessageTest() {
        Assertions.assertThat(event.getMessage()).isEqualTo(message);
    }

    @Test
    public void getErrorEventExceptionTest() {
        Assertions.assertThat(event.getException()).isEqualTo(error);
    }
}
