package com.example.reto2androidclient.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.reto2androidclient.R;
import com.example.reto2androidclient.model.Club;
import com.example.reto2androidclient.model.Event;

import java.util.List;

public class EventCardAdapter extends RecyclerView.Adapter<EventCardAdapter.EventCardViewHolder> {
    private List<Event> eventList;
    Context context;

    public EventCardAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    @Override
    public EventCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View eventCardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_event_card, parent, false);
        return new EventCardViewHolder(eventCardView);
    }

    @Override
    public void onBindViewHolder(EventCardViewHolder holder, final int position) {
        holder.dateTextView.setText(eventList.get(position).getDate().substring(0, eventList.get(position).getDate().indexOf("T")));
        holder.logoImageView.setImageResource(R.drawable.badass_coffee_avatar);
        holder.nameTextView.setText(eventList.get(position).getName());

        Club eventClub = eventList.get(position).getClub();
        if(eventClub != null)
            holder.organizerTextView.setText(eventClub.getFullName());
        else
            holder.organizerTextView.setText("Club name");

        String price = eventList.get(position).getTicketprice().toString() + "€";
        holder.priceTextView.setText(price);
        //holder.timeTextView.setText(eventList.get(position).getDate());
        holder.locationTextView.setText(eventList.get(position).getPlace());

        holder.logoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = eventList.get(position).getName();
                Toast.makeText(context, productName + " is selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventCardViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        ImageView logoImageView;
        TextView nameTextView;
        TextView organizerTextView;
        TextView priceTextView;
        //TextView timeTextView;
        TextView locationTextView;

        public EventCardViewHolder(View view) {
            super(view);
            dateTextView = view.findViewById(R.id.dateTextView);
            logoImageView = view.findViewById(R.id.logoImageView);
            nameTextView = view.findViewById(R.id.nameTextView);
            organizerTextView = view.findViewById(R.id.organizerTextView);
            priceTextView = view.findViewById(R.id.priceTextView);
            //timeTextView = view.findViewById(R.id.timeTextView);
            locationTextView = view.findViewById(R.id.locationTextView);
        }
    }
}