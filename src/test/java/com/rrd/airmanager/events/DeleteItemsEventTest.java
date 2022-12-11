package com.rrd.airmanager.events;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DeleteItemsEventTest {
    private final List<Integer> items = Collections.singletonList(1);
    private final DeleteItemsEvent<Integer> event = new DeleteItemsEvent<>(items);


    @Test
    public void getDeleteItemsEventItemsTest() {
        Assertions.assertThat(event.getItems()).isEqualTo(items);
    }

    @Test
    public void deleteItemEventResolvableTypeTest() {
        Assertions.assertThat(
                Objects.requireNonNull(event.getResolvableType()).getGeneric(0).resolve()
        ).isEqualTo(Integer.class);
    }
}
