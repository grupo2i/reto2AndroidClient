package com.example.reto2androidclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.reto2androidclient.R;
import com.example.reto2androidclient.model.Event;
import com.example.reto2androidclient.view.EventCardAdapter;

import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private EventCardAdapter mAdapter;
    private List<Event> mProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        //getting the recyclerview from xml
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Populate the products
        mProductList = new ArrayList<>();

        /*
        mProductList.add(new Event(new Date(), R.mipmap.logo1, "Event Name", "Organizer", 5.6f, "Location"));
        mProductList.add(new Event(new Date(), R.mipmap.logo2, "Event Name2", "Organizer2", 6.5f, "Location2"));
        for(int i = 3; i < 11; ++i) {
            if(i % 2 == 0)
                mProductList.add(new Event(new Date(), R.mipmap.logo1, "Event Name" + i, "Organizer" + i, 4.3f + i, "Location" + i));
            else
                mProductList.add(new Event(new Date(), R.mipmap.logo2, "Event Name" + i, "Organizer" + i, 4.7f + i, "Location" + i));
        }*/

        //set adapter to recyclerview
        mAdapter = new EventCardAdapter(mProductList,this);
        mRecyclerView.setAdapter(mAdapter);
    }
}