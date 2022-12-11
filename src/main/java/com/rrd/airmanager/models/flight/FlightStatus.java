package com.rrd.airmanager.models.flight;

public enum FlightStatus {
    IN_PROGRESS("In progress"), UPCOMING("Upcoming"), COMPLETED("Completed");

    private final String label;

    FlightStatus(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
