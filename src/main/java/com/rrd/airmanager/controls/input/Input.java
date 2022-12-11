package com.rrd.airmanager.controls.input;

public interface Input<T> {
    boolean validate();
    void setValue(T value);
    T getValue();
}
