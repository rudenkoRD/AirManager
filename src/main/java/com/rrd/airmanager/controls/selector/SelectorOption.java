package com.rrd.airmanager.controls.selector;

public record SelectorOption<T>(T option){
    @Override
    public String toString() {
        return option != null ? option.toString() : "All";
    }
}
