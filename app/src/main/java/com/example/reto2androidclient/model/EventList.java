package com.example.reto2androidclient.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name="events")
public class EventList {
    @ElementList(entry="event", inline=true)
    private List<Client> events;

    public List<Client> getClients() {
        return events;
    }

    public void setClients(List<Client> clients) {
        this.events = events;
    }
}
