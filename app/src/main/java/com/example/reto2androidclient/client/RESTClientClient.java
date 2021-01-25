package com.example.reto2androidclient.client;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Factory for {@link RESTClientInterface} interface.
 *
 * @author Aitor Fidalgo
 */
public class RESTClientClient {
    private static String BASE_URL = "http://192.168.1.132:11238/reto2Server/webresources/entity.client/";

    public static RESTClientInterface getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(interceptor);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();
        RESTClientInterface restClientInterface = retrofit.create(RESTClientInterface.class);

        return restClientInterface;
    }
}
