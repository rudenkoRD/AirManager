package com.rrd.airmanager.controls.input;

@FunctionalInterface
public interface Parser<T> {
    T apply(String t);
}
