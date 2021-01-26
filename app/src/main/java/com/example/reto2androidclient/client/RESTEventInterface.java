package com.example.reto2androidclient.client;

import com.example.reto2androidclient.model.EventList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RESTEventInterface {

    /**
     * Retrieves all Events from the database.
     *
     * @return A list of all clients.
     */
    @GET("getAllEvents")
    public Call<EventList> getAllEvents();
}
