package com.rrd.airmanager.models.plane;

public enum PlaneType {
    PRIVATE("Private"), CARGO("Cargo"), COMMERCIAL("Commercial");

    private final String label;

    PlaneType(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
