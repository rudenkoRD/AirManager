package com.rrd.airmanager.events;

import org.springframework.context.ApplicationEvent;

public class InfoEvent extends ApplicationEvent {
    public InfoEvent(String message) {
        super(message);
    }

    public String getMessage() {
        return (String) getSource();
    }
}
