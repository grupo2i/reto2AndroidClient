package com.example.reto2androidclient.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Root(name="event")
public class Event implements Serializable {

    @Element(name="id", required=false)
    private Integer id;
    @Element(name="name", required=false)
    private String name;
    @Element(name="date", required=false)
    private String date;
    @Element(name="place", required=false)
    private String place;
    @Element(name="ticketprice", required=false)
    private Float ticketprice;
    @Element(name="description", required=false)
    private String description;
    @Element(name="profileImage", required=false)
    private String profileImage;

    @Element(name="club", required=false)
    private Club club;
    @ElementList(entry="artists", inline=true, required = false)
    private Set<Artist> artists;
    @ElementList(entry="clients", inline=true, required = false)
    private Set<Client> clients;
    @ElementList(entry="ratings", inline=true, required = false)
    private Set<Rating> ratings;


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public Integer getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public Float getTicketprice() {
        return ticketprice;
    }

    public String getDescription() {
        return description;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setTicketprice(Float ticketprice) {
        this.ticketprice = ticketprice;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

}
