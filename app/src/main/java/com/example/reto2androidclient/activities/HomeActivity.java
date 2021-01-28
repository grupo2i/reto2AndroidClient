package com.example.reto2androidclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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

/**
 * Controller for the Home window.
 *
 * @author Aitor Fidalgo
 */
public class HomeActivity extends AppCompatActivity {

    private Client client;
    private ImageButton imageButtonHome, imageButtonSearch, imageButtonWishlist, imageButtonProfile;
    private RecyclerView mRecyclerView;
    private EventCardAdapter mAdapter;
    private List<Event> mProductList;
    private List<Event> events = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        client = (Client) getIntent().getExtras().getSerializable("CLIENT");
        events =  (List<Event>)getIntent().getExtras().getSerializable("Filtered");

        imageButtonHome = findViewById(R.id.imageButtonHomeClientProfile);
        imageButtonHome.setEnabled(false);

        imageButtonSearch = findViewById(R.id.imageButtonSearchHome);
        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intentToSearch = new Intent(HomeActivity.this, SearchActivity.class);
            intentToSearch.putExtra("CLIENT", client);
            startActivity(intentToSearch);
            }
        });

        imageButtonWishlist = findViewById(R.id.imageButtonWishlistClientProfile);
        imageButtonWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToWishlist = new Intent(HomeActivity.this, WishlistActivity.class);
                intentToWishlist.putExtra("CLIENT", client);
                startActivity(intentToWishlist);
            }
        });

        imageButtonProfile = findViewById(R.id.imageButtonProfileClientProfile);
        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToClientProfile = new Intent(HomeActivity.this, ClientProfileActivity.class);
                intentToClientProfile.putExtra("CLIENT", client);
                startActivity(intentToClientProfile);
            }
        });

        //getting the recyclerview from xml
        mRecyclerView = (RecyclerView) findViewById(R.id.homeRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Populate the products
        mProductList = new ArrayList<>();
        if(events != null) {
            mProductList.addAll(events);
            //set adapter to recyclerview
            mAdapter = new EventCardAdapter(mProductList, getApplicationContext(), client);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            RESTEventInterface restEventInterface = RESTEventClient.getClient();
            Call<EventList> callEvents = restEventInterface.getAllEvents();
            callEvents.enqueue(new Callback<EventList>() {
                @Override
                public void onResponse(Call<EventList> call, Response<EventList> response) {
                    switch(response.code()) {
                        case 200:
                            EventList evs = response.body();
                            for(Event event : evs.getEvents()) {
                                mProductList.add(event);
                            }
                            //set adapter to recyclerview
                            mAdapter = new EventCardAdapter(mProductList, getApplicationContext(), client);
                            mRecyclerView.setAdapter(mAdapter);
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), R.string.unexpectedError, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<EventList> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), R.string.unexpectedError, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}