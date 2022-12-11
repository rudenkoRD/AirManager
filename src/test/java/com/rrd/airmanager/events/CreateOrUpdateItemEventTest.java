package com.rrd.airmanager.events;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class CreateOrUpdateItemEventTest {
    private final CreateOrUpdateItemEvent<Integer> event = new CreateOrUpdateItemEvent<>(1);

    @Test
    public void getCreateOrUpdateItemEventEventItemTest() {
        Assertions.assertThat(event.getItem()).isEqualTo(1);
    }

    @Test
    public void createOrUpdateItemEventResolvableTypeTest() {
        Assertions.assertThat(
                Objects.requireNonNull(event.getResolvableType()).getGeneric(0).resolve()
        ).isEqualTo(Integer.class);
    }
}
