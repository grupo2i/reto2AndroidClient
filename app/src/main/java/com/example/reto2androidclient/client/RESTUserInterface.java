package com.example.reto2androidclient.client;

import com.example.reto2androidclient.model.Client;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RESTUserInterface {

    /**
     * Makes a sign in request to the server, access is granted if a Client object is returned.
     *
     * @param login Login of the Client trying to sign in.
     * @param password Password of the Client trying to sign in, encoded with RSA and in hexadecimal.
     * @return A Client object with all the registered data if access to the application is granted.
     */
    @GET("signIn/{login}/{password}")
    public Call<Client> signIn(@Path("login") String login, @Path("password") String password);

}
