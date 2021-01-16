package com.example.reto2androidclient.client;

import com.example.reto2androidclient.model.Client;
import com.example.reto2androidclient.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface RESTClientInterface {

    @PUT(".")
    public Call<ResponseBody> edit(@Body Client entity);
}
