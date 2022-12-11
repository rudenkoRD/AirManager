package com.rrd.airmanager.models.plane;

public enum PlaneFlightStatus {
    IN_FLIGHT("In flight"), ON_GROUND("On ground");

    private final String label;

    PlaneFlightStatus(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
