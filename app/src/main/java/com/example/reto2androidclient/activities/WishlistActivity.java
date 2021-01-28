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
import com.example.reto2androidclient.model.Client;
import com.example.reto2androidclient.model.Event;
import com.example.reto2androidclient.view.EventCardAdapter;

import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private EventCardAdapter mAdapter;
    private List<Event> mProductList;
    private Client client;

    private ImageButton imageButtonHome, imageButtonSearch, imageButtonWishlist, imageButtonProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        client = (Client) getIntent().getExtras().getSerializable("CLIENT");

        imageButtonHome = findViewById(R.id.imageButtonHomeClientProfile);
        imageButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToHome = new Intent(WishlistActivity.this, HomeActivity.class);
                intentToHome.putExtra("CLIENT", client);
                startActivity(intentToHome);
            }
        });

        imageButtonSearch = findViewById(R.id.imageButtonSearchHome);
        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToSearch = new Intent(WishlistActivity.this, SearchActivity.class);
                startActivity(intentToSearch);
            }
        });

        imageButtonWishlist = findViewById(R.id.imageButtonWishlistClientProfile);
        imageButtonWishlist.setEnabled(false);

        imageButtonProfile = findViewById(R.id.imageButtonProfileClientProfile);
        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToClientProfile = new Intent(WishlistActivity.this, ClientProfileActivity.class);
                intentToClientProfile.putExtra("CLIENT", client);
                startActivity(intentToClientProfile);
            }
        });

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
            mAdapter = new EventCardAdapter(mProductList, getApplicationContext(), client);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            Toast.makeText(getApplicationContext(), "No events in wishlist.", Toast.LENGTH_LONG).show();
        }
    }
}