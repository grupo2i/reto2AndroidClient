package com.example.reto2androidclient.client;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RESTClientClient {
    private static String BASE_URL = "http://192.168.20.183:8080/reto2Server/webresources/entity.client/";

    public static RESTClientInterface getClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();
        RESTClientInterface restClientInterface = retrofit.create(RESTClientInterface.class);

        return restClientInterface;
    }
}
