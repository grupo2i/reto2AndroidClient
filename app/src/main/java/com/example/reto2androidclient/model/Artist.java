package com.example.reto2androidclient.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Set;

@Root(name="artist")
public class Artist extends User implements Serializable {

    @ElementList(entry="socialNetwork", inline=true, required = false)
    private Set<SocialNetwork> socialNetworks;
    @Element(name = "musicGenre", required = false)
    private MusicGenre musicGenre;
    @ElementList(entry="events", inline=true, required = false)
    private Set<Event> events;


    public void setSocialNetworks(Set<SocialNetwork> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public void setMusicGenre(MusicGenre musicGenre) {
        this.musicGenre = musicGenre;
    }

    public Set<SocialNetwork> getSocialNetworks() {
        return socialNetworks;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public MusicGenre getMusicGenre() {
        return musicGenre;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Artist)) {
            return false;
        }
        Artist other = (Artist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "entity.artista[ id=" + id + " ]";
    }

}
