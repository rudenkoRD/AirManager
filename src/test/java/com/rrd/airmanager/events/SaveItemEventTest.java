package com.rrd.airmanager.events;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class SaveItemEventTest {
    private final SaveItemEvent<Integer> event = new SaveItemEvent<>(1);

    @Test
    public void getSaveItemEventItemTest() {
        Assertions.assertThat(event.getItem()).isEqualTo(1);
    }

    @Test
    public void saveItemEventResolvableTypeTest() {
        Assertions.assertThat(
                Objects.requireNonNull(event.getResolvableType()).getGeneric(0).resolve()
        ).isEqualTo(Integer.class);
    }
}
