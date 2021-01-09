package com.example.reto2androidclient.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Event implements Serializable {
    private Integer id;
    private String name;
    private Date date;
    private String place;
    private Float ticketprice;
    private String description;
    private String profileImage;

    private Club club;
    private Set<Artist> artists;
    private Set<Client> clients;
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

    public Date getDate() {
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

    public void setDate(Date date) {
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
