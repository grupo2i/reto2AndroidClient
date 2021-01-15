package com.example.reto2androidclient.client;

import com.example.reto2androidclient.model.Client;
import com.example.reto2androidclient.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RESTUserInterface {

    @GET("signIn/{login}/{password}")
    public Call<Client> signIn(@Path("login") String login, @Path("password") String password);
}
