package com.example.reto2androidclient.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author Martin Angulo
 */
@Root(name="client")
public class Client extends User implements Serializable {

    @ElementList(entry = "events", required = false, inline = true)
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
