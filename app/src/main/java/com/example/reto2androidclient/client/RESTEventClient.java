package com.example.reto2androidclient.client;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Factory for {@link RESTEventInterface} interface.
 *
 * @author Martin Angulo
 */
public class RESTEventClient {
    private static String BASE_URL = "http://192.168.21.122:8080/reto2Server/webresources/entity.event/";

    public static RESTEventInterface getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(interceptor);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();
        RESTEventInterface restEventInterface = retrofit.create(RESTEventInterface.class);

        return restEventInterface;
    }
}
