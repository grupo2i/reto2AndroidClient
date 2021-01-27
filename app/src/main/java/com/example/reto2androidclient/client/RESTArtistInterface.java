package com.example.reto2androidclient.client;

import com.example.reto2androidclient.model.Artist;
import com.example.reto2androidclient.model.ArtistList;
import com.example.reto2androidclient.model.Client;
import com.example.reto2androidclient.model.ClientList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Contains the methods to make request to the server.
 *
 * @author Ander Vicente
 */
public interface RESTArtistInterface {

    /**
     * Makes a request to create a new Client in the database with the specified data.
     *
     * @param artist New Client to be registered.
     * @param currentDate Current date of the registration used to set value to the artists
     *                   attributes lastAccess and lastPasswordChange
     * @return Http response code.
     */
    @POST("{currentDate}")
    public Call<ResponseBody> create(@Body Artist artist, @Path("currentDate") String currentDate);

    /**
     * Updates an existing Artist in the database with the specified data.
     *
     * @param entity Artist containing the new data.
     * @return Http response code.
     */
    @PUT(".")
    public Call<ResponseBody> edit(@Body Artist entity);

    /**
     * Retrieves all Artist from the database.
     *
     * @return A list of all artist.
     */
    @GET("getAllArtists")
    public Call<ArtistList> getAllArtists();


}
