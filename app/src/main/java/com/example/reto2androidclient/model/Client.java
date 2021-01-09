package com.example.reto2androidclient.model;

import java.io.Serializable;
import java.util.Set;

public class Client extends User implements Serializable {
    private Set<Event> events;

    /**
     * @return the events
     */
    public Set<Event> getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
