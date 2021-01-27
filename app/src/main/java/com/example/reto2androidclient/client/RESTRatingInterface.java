package com.example.reto2androidclient.client;

import com.example.reto2androidclient.model.Rating;
import com.example.reto2androidclient.model.RatingList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Contains the methods to make request to the server.
 *
 * @author Martin Angulo
 */
public interface RESTRatingInterface {
    /**
     * Updates an existing Rating in the database with the specified data.
     *
     * @param entity Rating containing the new data.
     * @return Http response code.
     */
    @PUT(".")
    public Call<ResponseBody> edit(@Body Rating entity);

    /**
     * Saves a new Rating in the database with the specified data.
     *
     * @param entity Rating containing to create.
     * @return Http response code.
     */
    @POST(".")
    public Call<ResponseBody> create(@Body Rating entity);

    /**
     * Retrieves all Ratings associated to the client from the database.
     *
     * @param id id of the client.
     * @return A list of the ratings.
     */
    @GET("getAllRatingsByUserId/{id}")
    public Call<RatingList> getAllRatingsByUserId(@Path("id") Integer id);

    /**
     * Retrieves all Ratings associated to the event from the database.
     *
     * @param id id of the event.
     * @return A list of the ratings.
     */
    @GET("getAllRatingsByEventId/{id}")
    public Call<RatingList> getAllRatingsByEventId(@Path("id") Integer id);
}
