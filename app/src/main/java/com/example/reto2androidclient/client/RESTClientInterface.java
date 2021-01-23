package com.example.reto2androidclient.client;

import com.example.reto2androidclient.model.Client;
import com.example.reto2androidclient.model.ClientList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Contains the methods to make request to the server.
 *
 * @author Aitor Fidalgo
 */
public interface RESTClientInterface {

    /**
     * Makes a request to create a new Client in the database with the specified data.
     *
     * @param client New Client to be registered.
     * @param currentDate Current date of the registration used to set value to the clients
     *                   attributes lastAccess and lastPasswordChange
     * @return Http response code.
     */
    @POST("{currentDate}")
    public Call<ResponseBody> create(@Body Client client, @Path("currentDate") String currentDate);

    /**
     * Updates an existing Client in the database with the specified data.
     *
     * @param entity Client containing the new data.
     * @return Http response code.
     */
    @PUT(".")
    public Call<ResponseBody> edit(@Body Client entity);

    /**
     * Retrieves all Clients from the database.
     *
     * @return A list of all clients.
     */
    @GET("getAllClients")
    public Call<ClientList> getAllClients();

    /**
     * Updates the Client with the specified email with a random password
     * and sends an email to notify it.
     *
     * @param email The specifies email.
     */
    @PUT("recoverPassword/{email}")
    public Call<ResponseBody> recoverPassword(@Path("email") String email);

}
