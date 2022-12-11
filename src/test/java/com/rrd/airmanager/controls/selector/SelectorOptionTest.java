package com.rrd.airmanager.controls.selector;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class SelectorOptionTest {
    @Test
    public void emptySelectorOptionToStringTest() {
        SelectorOption<Integer> option = new SelectorOption<>(null);

        Assertions.assertThat(option.toString()).isEqualTo("All");
    }

    @Test
    public void selectorOptionToStringTest() {
        SelectorOption<Integer> option = new SelectorOption<>(1);

        Assertions.assertThat(option.toString()).isEqualTo("1");
    }
}
