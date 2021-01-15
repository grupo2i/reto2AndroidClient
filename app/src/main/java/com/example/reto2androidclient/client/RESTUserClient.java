package com.example.reto2androidclient.client;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RESTUserClient {
    private static String BASE_URL = "http://192.168.21.31:11238/reto2Server/webresources/entity.user/";

    public static RESTUserInterface getClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();
        RESTUserInterface restUserInterface = retrofit.create(RESTUserInterface.class);

        return restUserInterface;
    }
}
