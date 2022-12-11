package com.rrd.airmanager.events;

import org.springframework.context.ApplicationEvent;

public class ErrorEvent extends ApplicationEvent {
    final Throwable exception;

    public ErrorEvent(String message, Throwable exception) {
        super(message);
        this.exception = exception;
    }

    public String getMessage() {
        return (String) getSource();
    }

    public Throwable getException() {
        return exception;
    }
}
