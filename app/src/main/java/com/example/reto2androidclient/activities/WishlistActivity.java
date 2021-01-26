package com.example.reto2androidclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.reto2androidclient.R;
import com.example.reto2androidclient.client.RESTEventClient;
import com.example.reto2androidclient.client.RESTEventInterface;
import com.example.reto2androidclient.model.Client;
import com.example.reto2androidclient.model.Event;
import com.example.reto2androidclient.model.EventList;
import com.example.reto2androidclient.view.EventCardAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private EventCardAdapter mAdapter;
    private List<Event> mProductList;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        client = (Client) getIntent().getExtras().getSerializable("CLIENT");

        //getting the recyclerview from xml
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Populate the products
        mProductList = new ArrayList<>();

        if(client.getEvents() != null) {
            for(Event event : client.getEvents()) {
                mProductList.add(event);
            }
            //set adapter to recyclerview
            mAdapter = new EventCardAdapter(mProductList, getApplicationContext());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            Toast.makeText(getApplicationContext(), "No events in wishlist.", Toast.LENGTH_LONG).show();
        }

        //Get all events from database
        /*
        RESTEventInterface restEventInterface = RESTEventClient.getEvent();
        Call<EventList> callEvents = restEventInterface.getAllEvents();
        callEvents.enqueue(new Callback<EventList>() {
            @Override
            public void onResponse(Call<EventList> call, Response<EventList> response) {
                switch(response.code()) {
                    case 200:
                        EventList events = response.body();
                        for(Event event : events.getEvents()) {
                            mProductList.add(event);
                            //set adapter to recyclerview
                            mAdapter = new EventCardAdapter(mProductList, getApplicationContext());
                            mRecyclerView.setAdapter(mAdapter);
                        }
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<EventList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.unexpectedError, Toast.LENGTH_LONG).show();
            }
        });
        */
    }
}