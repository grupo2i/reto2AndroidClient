package com.example.reto2androidclient.client;

import com.example.reto2androidclient.model.Club;
import com.example.reto2androidclient.model.ClubList;
import com.example.reto2androidclient.model.Event;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RESTClubInterface {


    /**
     * Makes a request to create a new Event in the database with the specified data.
     *
     * @param club New Client to be registered.
     * @param currentDate Current date of the registration used to set value to the clubs
     *                   attributes lastAccess and lastPasswordChange
     * @return Http response code.
     */
    @POST("{currentDate}")
    public Call<ResponseBody> create(@Body Club club, @Path("currentDate") String currentDate);

    /**
     * Updates an existing Artist in the database with the specified data.
     *
     * @param entity Club containing the new data.
     * @return Http response code.
     */
    @PUT(".")
    public Call<ResponseBody> edit(@Body Club entity);
    /**
     * Retrieves all Clubs from the database.
     *
     * @return A list of all clubs.
     */
    @GET("getAllClubs")
    public Call<ClubList> getAllClubs();
}
