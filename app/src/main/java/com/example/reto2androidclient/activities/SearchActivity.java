package com.example.reto2androidclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.reto2androidclient.R;
import com.example.reto2androidclient.client.RESTArtistClient;
import com.example.reto2androidclient.client.RESTArtistInterface;
import com.example.reto2androidclient.client.RESTClubClient;
import com.example.reto2androidclient.client.RESTClubInterface;
import com.example.reto2androidclient.client.RESTEventClient;
import com.example.reto2androidclient.client.RESTEventInterface;
import com.example.reto2androidclient.model.Artist;
import com.example.reto2androidclient.model.ArtistList;
import com.example.reto2androidclient.model.Client;
import com.example.reto2androidclient.model.Club;
import com.example.reto2androidclient.model.ClubList;
import com.example.reto2androidclient.model.Event;
import com.example.reto2androidclient.model.EventList;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    EditText searchDate;
    TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ImageButton imageButtonHome, imageButtonSearch, imageButtonWishlist, imageButtonProfile;
    private Client client;
    private Button search;
    private EditText txtArtistName, txtClub;
    private TextView tvDate;
    ArtistList artists;
    List<Event> events;
    ClubList clubs;
    boolean artistFound=false;
    boolean clubFound=false;
    boolean dateFound=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("Filter events");
        search = findViewById(R.id.search);
        //mDisplayDate = (TextView) findViewById(R.id.tvDate);
        txtArtistName = (EditText) findViewById(R.id.txtArtistName);
        txtClub = (EditText) findViewById(R.id.txtClub);
        //tvDate = (TextView) findViewById(R.id.tvDate);
        imageButtonHome = findViewById(R.id.imageButtonHomeClientProfile);
        imageButtonHome.setEnabled(false);
        imageButtonSearch = findViewById(R.id.imageButtonSearchHome);

        client = (Client) getIntent().getExtras().getSerializable("CLIENT");

        //GETTING ALL EVENTS..
        RESTEventInterface restEventInterface = RESTEventClient.getClient();
        Call<EventList> callEvents = restEventInterface.getAllEvents();
        callEvents.enqueue(new Callback<EventList>() {
            @Override
            public void onResponse(Call<EventList> call, Response<EventList> response) {
                switch (response.code()) {
                    case 200:
                       EventList eventList = response.body();
                       events=eventList.getEvents();
                       break;
                    default:
                        Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<EventList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.unexpectedError, Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });

        //GETTING ALL ARTISTS...
        RESTArtistInterface restArtistInterface = RESTArtistClient.getArtist();
        Call<ArtistList> callArtists = restArtistInterface.getAllArtists();
        callArtists.enqueue(new Callback<ArtistList>() {
            @Override
            public void onResponse(Call<ArtistList> call, Response<ArtistList> response) {
                switch (response.code()) {
                    case 200:
                        artists = response.body();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArtistList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.unexpectedError, Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });

        //GETTING ALL CLUBS...
        RESTClubInterface restClubInterface = RESTClubClient.getClubs();
        Call<ClubList> callClubs = restClubInterface.getAllClubs();
        callClubs.enqueue(new Callback<ClubList>() {
            @Override
            public void onResponse(Call<ClubList> call, Response<ClubList> response) {
                switch (response.code()) {
                    case 200:
                        clubs = response.body();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ClubList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.unexpectedError, Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });

        imageButtonWishlist = findViewById(R.id.imageButtonWishlistClientProfile);
        imageButtonWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToHome = new Intent(SearchActivity.this, WishlistActivity.class);
                intentToHome.putExtra("CLIENT", client);
                startActivity(intentToHome);
            }
        });

        imageButtonProfile = findViewById(R.id.imageButtonProfileClientProfile);
        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToClientProfile = new Intent(SearchActivity.this, ClientProfileActivity.class);
                intentToClientProfile.putExtra("CLIENT", client);
                startActivity(intentToClientProfile);
            }
        });
        imageButtonHome.setEnabled(true);
        imageButtonHome = findViewById(R.id.imageButtonHomeClientProfile);
        imageButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToHome = new Intent(SearchActivity.this, HomeActivity.class);
                intentToHome.putExtra("CLIENT", client);
                startActivity(intentToHome);
            }
        });
        /*mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SearchActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            private static final String TAG = "Mamaduk";

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: yyy-mm-dd: " + year + "-" + month + "-" + day);
                String date = year + "-" + month + "-" + day;
                mDisplayDate.setText(date);
            }
        };*/

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Event> searchEvents = new ArrayList<>();
                if(txtArtistName.getText().length() != 0) {
                    for(Event e : events) {
                        Set<Artist> eventArtists = e.getArtists();
                        for(Artist a : eventArtists) {
                            if(a.getFullName().contains(txtArtistName.getText())) {
                                searchEvents.add(e);
                                break;
                            }
                        }
                    }
                }

                if(txtClub.getText().length() != 0) {
                    for(Event e : events) {
                        if(e.getClub().getFullName().contains(txtArtistName.getText())) {
                            searchEvents.add(e);
                            break;
                        }
                    }
                }

                events = searchEvents;
                //ARTIST
                /*
                boolean esta=false;
                Artist artist = new Artist();
                if(txtArtistName.getText().length()>0){
                    findArtist(txtArtistName.getText().toString());
                    if(artistFound) {
                        for (Event e : events) {
                            for (Event artistEvent : artist.getEvents()) {
                                if (e.equals(artistEvent)) {
                                    esta = true;
                                    break;
                                }
                            }
                            if (!esta) {
                                events.remove(e);
                            }
                        }
                    }
                }
                esta=false;
                //CLUB
                Club club  = new Club();
                if(txtClub.getText().toString().length()>0){
                    findClub(txtClub.getText().toString());
                    if(clubFound) {
                        for (Event e : events) {
                            for (Event clubEvent : club.getEvents()) {
                                if (e.equals(clubEvent)) {
                                    esta = true;
                                    break;
                                }
                            }
                            if (!esta) {
                                events.remove(e);
                            }
                        }
                    }
                }esta=false;
                //DATE
                try{
                Date dateUser = null;
                    Date dateEvents = null;
                if(tvDate.getText().toString().length()>0){
                    SimpleDateFormat formater=new SimpleDateFormat("yyyy/MM/dd");
                        dateUser = formater.parse(tvDate.getText().toString());
                    if(dateFound){
                        for (Event e : events) {
                            dateEvents=formater.parse(e.getDate());
                            if (dateEvents.equals(dateUser)) {
                                esta = true;
                                break;
                            }
                            if (!esta) {
                                events.remove(e);
                            }
                        }
                    }
                }
                }catch (ParseException p){
                    Toast.makeText(getApplicationContext(), R.string.unexpectedError, Toast.LENGTH_LONG).show();
                }
                if(events.size()==0){
                    Toast.makeText(getApplicationContext(), R.string.unexpectedError, Toast.LENGTH_LONG).show();
                }*/
                Intent intentToHome = new Intent(SearchActivity.this, HomeActivity.class);
                intentToHome.putExtra("CLIENT", client);
                intentToHome.putExtra("Filtered", (Serializable)events);
                startActivity(intentToHome);
            }
        });
    }
    private void findArtist(String artistName) {
        if(artists.getArtists().contains(artistName)){
            artistFound=true;
        }
    }
    private void findClub(String clubName) {
        if(clubs.getClubs().contains(clubName)){
            clubFound=true;
        }
    }
    private void findDate(Date date) {
        if(events.contains(date)){
            dateFound=true;
        }
    }
}