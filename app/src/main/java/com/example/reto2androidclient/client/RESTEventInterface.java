package com.example.reto2androidclient.client;

import com.example.reto2androidclient.model.Client;
import com.example.reto2androidclient.model.Event;
import com.example.reto2androidclient.model.EventList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface RESTEventInterface {

    /**
     * Retrieves all Events from the database.
     *
     * @return A list of all clients.
     */
    @GET("getAllEvents")
    public Call<EventList> getAllEvents();

    /**
     * Updates an existing Client in the database with the specified data.
     *
     * @param entity Client containing the new data.
     * @return Http response code.
     */
    @PUT(".")
    public Call<ResponseBody> edit(@Body Event entity);
}
