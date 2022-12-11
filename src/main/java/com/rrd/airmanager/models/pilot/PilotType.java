package com.rrd.airmanager.models.pilot;

public enum PilotType {
    CAPTAIN("Captain"), SECOND_PILOT("Second pilot");

    private final String label;

    PilotType(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
